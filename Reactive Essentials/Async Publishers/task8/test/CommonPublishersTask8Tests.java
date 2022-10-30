import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.test.StepVerifier;

public class CommonPublishersTask8Tests {

	@Test
	public void testSolution1() {
		Publisher<Integer> sequence = Task.createSequence(10, 0, 20);

		StepVerifier.create(sequence)
		            .expectNext(10)
		            .expectComplete()
		            .verify(Duration.ofMillis(100));
	}

	@Test
	public void testSolution2() {
		Publisher<Integer> sequence = Task.createSequence(0, 0, 20);

		StepVerifier.create(sequence)
		            .expectNext(0)
		            .expectComplete()
		            .verify(Duration.ofMillis(100));
	}

	@Test
	public void testSolution3() {
		Publisher<Integer> sequence = Task.createSequence(20, 0, 20);

		StepVerifier.create(sequence)
		            .expectNext(20)
		            .expectComplete()
		            .verify(Duration.ofMillis(100));
	}

	@Test
	public void testSolution4() {
		Publisher<Integer> sequence = Task.createSequence(Integer.MIN_VALUE, 0, 20);

		StepVerifier.create(sequence)
		            .expectError()
		            .verify(Duration.ofMillis(100));
	}

	@Test
	public void testSolution5() {
		Publisher<Integer> sequence = Task.createSequence(Integer.MAX_VALUE, 0, 20);

		StepVerifier.create(sequence)
		            .expectError()
		            .verify(Duration.ofMillis(100));
	}

	@Test
	public void testSolution6() {
		Publisher<Integer> sequence = Task.createSequence(21, 0, 20);

		StepVerifier.create(sequence)
		            .expectError()
		            .verify(Duration.ofMillis(100));
	}

	@Test
	public void testSolution7() {
		Publisher<Integer> sequence = Task.createSequence(-1, 0, 20);

		StepVerifier.create(sequence)
		            .expectError()
		            .verify(Duration.ofMillis(100));
	}
}