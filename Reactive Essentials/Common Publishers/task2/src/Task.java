import reactor.core.publisher.Flux;

public class Task {

	public static Flux<String> createSequenceOfASingleElement(String element) {
		return Flux.just(element);
	}
}