import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Tests {

	@Test
	public void testFluxEmpty() {
		Flux<Long> sequence = Task.createFluxEmittingOnlyOnComplete();

		StepVerifier.create(sequence)
		            .expectComplete()
		            .verify(Duration.ofSeconds(1));
	}

	@Test
	public void testFluxNever() {
		Flux<Void> sequence = Task.createFluxWhichNeverEmits();

		StepVerifier.create(sequence)
		            .expectSubscription()
		            .expectNoEvent(Duration.ofMillis(1000))
		            .thenCancel()
		            .verify();
	}

	@Test
	public void testMonoEmpty() {
		Mono<Long> sequence = Task.createMonoEmittingOnlyOnComplete();

		StepVerifier.create(sequence)
		            .expectComplete()
		            .verify(Duration.ofSeconds(1));
	}

	@Test
	public void testMonoNever() {
		Mono<Void> sequence = Task.createMonoWhichNeverEmits();

		StepVerifier.create(sequence)
		            .expectSubscription()
		            .expectNoEvent(Duration.ofMillis(1000))
		            .thenCancel()
		            .verify();
	}
}