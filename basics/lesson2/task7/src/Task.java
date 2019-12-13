import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Task {

	public static Mono<Long> transformSequence(Flux<Long> flux) {
		return flux.next();
	}
}