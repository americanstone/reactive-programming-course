import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.test.StepVerifier;

public class CommonPublishersTask7Tests {

	@Test
	public void testSolution() {
		Publisher<String> sequence =
				Task.createSequence(CompletableFuture.supplyAsync(() -> "test"));

		StepVerifier.create(sequence)
		            .expectNext("test")
		            .expectComplete()
		            .verify(Duration.ofMillis(100));
	}
}