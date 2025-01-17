package service.external;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;
import service.CryptoService;
import service.external.utils.PriceMessageUnpacker;
import service.external.utils.TradeMessageUnpacker;

public class CryptoCompareService implements CryptoService {
    public static final int CACHE_SIZE = 3;

    private final Flux<Map<String, Object>> reactiveCryptoListener;

    public CryptoCompareService() {
        reactiveCryptoListener = CryptoCompareClient
                .connect(
                        Flux.just("5~CCCAGG~BTC~USD", "0~Coinbase~BTC~USD", "0~Cexio~BTC~USD"),
                        Arrays.asList(new PriceMessageUnpacker(), new TradeMessageUnpacker())
                )
                .transform(CryptoCompareService::provideResilience)
                .transform(CryptoCompareService::provideCaching);
    }

    public Flux<Map<String, Object>> eventsStream() {
        return reactiveCryptoListener;
    }

    // TODO: implement resilience such as retry with delay
    public static <T> Flux<T> provideResilience(Flux<T> input) {
        return input.retryWhen(
            Retry.backoff(100, Duration.ofMillis(500))
                 .maxBackoff(Duration.ofMillis(2000))
        );
    }


    // TODO: implement caching of 3 last elements & multi subscribers support
    public static <T> Flux<T> provideCaching(Flux<T> input) {
        return input.replay(CACHE_SIZE).autoConnect(0);
    }
}
