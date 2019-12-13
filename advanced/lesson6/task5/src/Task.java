import java.time.Duration;
import java.util.concurrent.Callable;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class Task {

	public static Publisher<String> timeoutBlockingOperation(Callable<String> longRunningCall,
			Duration duration,
			String fallback) {
		return Mono.fromCallable(longRunningCall)
		           .subscribeOn(Schedulers.elastic())
		           .timeout(duration, Mono.just(fallback));
	}
}