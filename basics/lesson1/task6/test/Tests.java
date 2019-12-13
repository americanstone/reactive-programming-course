import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class Tests {

	@Test
	public void testSolution() {
		StepVerifier.withVirtualTime(() -> Task.createSequence(Duration.ofMillis(10)))
		            .expectSubscription()
		            .expectNoEvent(Duration.ofMillis(10))
		            .expectNext(0L)
		            .thenAwait(Duration.ofSeconds(1))
		            .expectNextCount(100)
		            .thenCancel()
		            .verify(Duration.ofMillis(100));
	}
}