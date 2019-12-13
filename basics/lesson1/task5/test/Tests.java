import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Tests {

	@Test
	public void testSolution() {
		Mono<String> sequence = Task.createSequence(() -> "test");

		StepVerifier.create(sequence)
		            .expectNext("test")
		            .expectComplete()
		            .verify(Duration.ofMillis(100));
	}
}