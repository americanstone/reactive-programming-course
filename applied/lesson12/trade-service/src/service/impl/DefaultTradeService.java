package service.impl;

import java.time.Duration;
import java.util.Map;
import java.util.logging.Logger;

import domain.Trade;
import domain.utils.DomainMapper;
import dto.MessageDTO;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import repository.TradeRepository;
import service.CryptoService;
import service.TradeService;
import service.utils.MessageMapper;

public class DefaultTradeService implements TradeService {

	private static final Logger logger = Logger.getLogger("trade-service");

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
				    .delayUntil(__ -> intervalNotifier.next()),
				e -> delayNotifier.zipWith(Mono.delay(Duration.ofMillis(1000)))
			)
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
							.log("trade1")
							.timeout(Duration.ofSeconds(1))
							.retryBackoff(100, Duration.ofMillis(500),
									Duration.ofMillis(5000))
							.thenReturn(1),
						tradeRepository2
							.saveAll(trades)
							.log("trade2")
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
