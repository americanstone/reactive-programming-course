import reactor.core.publisher.Flux;

public class Task {

	public static Flux<Integer> createSequence() {
		return Flux.range(0, 20);
	}
}