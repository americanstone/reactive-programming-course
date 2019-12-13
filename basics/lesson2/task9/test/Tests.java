import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Tests {

	@Test
	public void testSolution() {
		Mono<Void> sequence =
				Task.waitUntilFluxCompletion(Flux.interval(Duration.ofMillis(5))
				                                 .take(100));

		StepVerifier.create(sequence)
		            .expectComplete()
		            .verify(Duration.ofMillis(10000));
	}
}