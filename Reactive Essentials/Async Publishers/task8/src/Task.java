import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public class Task {

	public static final IllegalArgumentException VALUE_OUT_OF_BOUNDS =
			new IllegalArgumentException("Value out of bounds");

	public static Publisher<Integer> createSequence(int value, int min, int max) {
		if (value < min || value > max) {
			return Mono.error(VALUE_OUT_OF_BOUNDS);
		}

		return Mono.just(value);
	}
}