import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.PublisherProbe;

public class CommonOperatorsTask2Tests {

	@Test
	public void testSolution() {
		PublisherProbe<String> probe = PublisherProbe.of(Flux.just("1", "10", "100", "1000", "10000"));
		probe
				.flux()
				.transform(Task::transformSequence)
				.as(StepVerifier::create)
				.expectNext("1000", "10000")
				.expectComplete()
				.verify(Duration.ofMillis(100));

		probe.assertWasSubscribed();
		probe.assertWasRequested();
	}
}