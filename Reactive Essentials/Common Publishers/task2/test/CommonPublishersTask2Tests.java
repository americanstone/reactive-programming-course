import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class CommonPublishersTask2Tests {

	@Test
	public void testSolution() {
		Flux<String> sequence = Task.createSequenceOfASingleElement("test");

		StepVerifier.create(sequence)
		            .expectNext("test")
		            .expectComplete()
		            .verify(Duration.ofMillis(100));
	}
}