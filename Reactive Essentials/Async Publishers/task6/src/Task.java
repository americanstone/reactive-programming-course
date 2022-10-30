import java.time.Duration;

import reactor.core.publisher.Flux;

public class Task {

	public static Flux<Long> createSequence(Duration duration) {
		return Flux.interval(duration);
	}
}