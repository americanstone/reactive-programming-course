import java.time.Duration;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Tests {

	@Test
	public void testSolution() {
		Flux<String> sequence = Task.createSequence(Stream.of("1", "2", "3"));

		StepVerifier.create(sequence)
		            .expectNext("1", "2", "3")
		            .expectComplete()
		            .verify(Duration.ofMillis(100));
	}

	static final long[] SEQUENCE =
			{0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584,
					4181};
}