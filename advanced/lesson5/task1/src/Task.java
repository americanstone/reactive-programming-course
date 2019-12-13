import reactor.core.publisher.Flux;

public class Task {

	public static Flux<String> dropElementsOnBackpressure(Flux<String> upstream) {
		return upstream.onBackpressureDrop();
	}
}