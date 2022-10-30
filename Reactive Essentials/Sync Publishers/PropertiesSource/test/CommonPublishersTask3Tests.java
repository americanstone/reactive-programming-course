import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class CommonPublishersTask3Tests {

	@Test
	public void testSolution() {
		Object sequence = PropertiesSourceTask.createSequence();

		if (!(sequence instanceof Flux)) {
			Assertions.fail("Unexpected sequence type");
		}

		StepVerifier.create(((Flux<Property<?>>) sequence).map(Property::name))
		            .expectNext("1", "2", "3")
		            .expectComplete()
		            .verify(Duration.ofMillis(100));
	}
}