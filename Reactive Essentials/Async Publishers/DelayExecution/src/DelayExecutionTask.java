import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DelayExecutionTask {

	public static Mono<Long> pauseExecution(long pauseDurationInMillis) {
		return Mono.delay(Duration.ofMillis(pauseDurationInMillis));
	}
}