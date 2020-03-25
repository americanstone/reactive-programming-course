package service.impl;

import java.time.Duration;
import java.util.Map;
import java.util.logging.Level;

import com.mongodb.MongoException;
import domain.Trade;
import domain.utils.DomainMapper;
import dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.retry.Retry;
import repository.TradeRepository;
import service.CryptoService;
import service.TradeService;
import service.utils.MessageMapper;

public class DefaultTradeService implements TradeService {

	private static final Logger logger = LoggerFactory.getLogger("trade-service");

	private final Flux<MessageDTO<MessageDTO.Trade>> sharedStream;

	public DefaultTradeService(CryptoService service,
			TradeRepository jdbcRepository,
			TradeRepository mongoRepository
	) {
		sharedStream = service.eventsStream()
		                      .transform(this::filterAndMapTradingEvents)
		                      .transform(trades -> Flux.merge(
	                              trades,
			                      trades.transform(this::mapToDomainTrade)
			                            .as(f -> this.resilientlyStoreByBatchesToAllRepositories(
		                                    f,
				                            jdbcRepository,
				                            mongoRepository
			                            ))
				                        .subscribeWith(MonoProcessor.create())
				                        .then(Mono.empty())
		                      ));
	}

	@Override
	public Flux<MessageDTO<MessageDTO.Trade>> tradesStream() {
		return sharedStream;
	}

	Flux<MessageDTO<MessageDTO.Trade>> filterAndMapTradingEvents(Flux<Map<String, Object>> input) {
		// TODO: Add implementation to produce trading events
		return input.handle((m, s) -> {
			if (MessageMapper.isTradeMessageType(m)) {
				s.next(MessageMapper.mapToTradeMessage(m));
			}
		});
	}

	Flux<Trade> mapToDomainTrade(Flux<MessageDTO<MessageDTO.Trade>> input) {
		// TODO: Add implementation to mapping to com.example.part_10.domain.Trade
		return input.map(DomainMapper::mapToDomain);
	}

	Mono<Void> resilientlyStoreByBatchesToAllRepositories(
			Flux<Trade> input,
			TradeRepository tradeRepository1,
			TradeRepository tradeRepository2) {
		EmitterProcessor<Long> delayNotifier =
				EmitterProcessor.create(false);
		delayNotifier.onNext(0L);
		EmitterProcessor<Long> intervalNotifier =
				EmitterProcessor.create(false);
		intervalNotifier.onNext(0L);

		return input
			.bufferWhen(
				Flux.interval(Duration.ZERO, Duration.ofSeconds(1))
				    .onBackpressureDrop()
				    .concatMap(v -> Mono.just(v).delayUntil(__ -> intervalNotifier.next()), 1),
				e -> delayNotifier.zipWith(Mono.delay(Duration.ofMillis(1000)))
			)
			.doOnNext(__ -> logger.warn(".buffer(Duration.ofMillis(100)) onNext(" + __ + ")"))
			.concatMap(trades -> {
				if (trades.isEmpty()) {
					return Mono
						.empty()
						.doFirst(() -> intervalNotifier.onNext(0L))
						.then(Mono.fromRunnable(() -> delayNotifier.onNext(0L)));
				}

				return Mono
					.zip(
						tradeRepository1
							.saveAll(trades)
							.timeout(Duration.ofSeconds(1))
							.retryWhen(Retry
								.onlyIf(rc -> {
									Throwable exception = rc.exception();
									if (exception instanceof MongoException) {
										return ((MongoException) exception).getCode() != 11000;
									}

									return true;
								})
								.retryMax(100)
								.randomBackoff(Duration.ofMillis(100), Duration.ofSeconds(5))
							)
							.onErrorResume(MongoException.class, t -> Mono.empty())
							.thenReturn(1),
						tradeRepository2
							.saveAll(trades)
							.log("test", Level.INFO)
							.timeout(Duration.ofSeconds(1))
							.retryBackoff(100, Duration.ofMillis(500),
									Duration.ofMillis(5000))
							.thenReturn(1)
					)
					.doFirst(() -> intervalNotifier.onNext(0L))
					.then(Mono.fromRunnable(() -> delayNotifier.onNext(0L)));
			})
			.then();
	}

}
