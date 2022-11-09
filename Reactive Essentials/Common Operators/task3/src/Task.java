import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.stream.Stream;

public class Task {

	public static Flux<Character> createSequence(Flux<String> stringFlux) {
		return stringFlux.concatMapIterable(word -> Arrays.asList(word.split("")))
		                 .map(letter -> letter.charAt(0));
	}
}