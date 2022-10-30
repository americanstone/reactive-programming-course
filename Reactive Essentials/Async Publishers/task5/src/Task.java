import java.util.concurrent.Callable;

import reactor.core.publisher.Mono;

public class Task {

	public static Mono<String> createSequence(Callable<String> callable) {
		return Mono.fromCallable(callable);
	}
}