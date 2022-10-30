import reactor.core.publisher.Flux;

public class Task {

	public static Flux<String> createSequence(String... args) {
		return Flux.fromArray(args);
	}
}