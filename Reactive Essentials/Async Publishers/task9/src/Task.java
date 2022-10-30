import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Task {

	public static Flux<Long> createFluxEmittingOnlyOnComplete() {
		return Flux.empty();
	}

	public static Flux<Void> createFluxWhichNeverEmits() {
		return Flux.never();
	}

	public static Mono<Long> createMonoEmittingOnlyOnComplete() {
		return Mono.empty();
	}

	public static Mono<Void> createMonoWhichNeverEmits() {
		return Mono.never();
	}
}