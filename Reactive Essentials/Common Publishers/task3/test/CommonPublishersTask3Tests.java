import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class CommonPublishersTask3Tests {

	@Test
	public void testSolution() {
		Flux<String> sequence = Task.createSequence("1", "2", "3");

		StepVerifier.create(sequence)
		            .expectNext("1", "2", "3")
		            .expectComplete()
		            .verify(Duration.ofMillis(100));
	}
}