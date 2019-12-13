import java.util.stream.Stream;

import reactor.core.publisher.Flux;

public class Task {

	public static Flux<String> createSequence(Stream<String> stream) {
		return Flux.fromStream(stream);
	}
}