import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class Tests {

	@Test
	public void testSolution() {
		StepVerifier.create(Task.timeoutBlockingOperation(() -> {
			try {
				Thread.sleep(1000000);
			}
			catch (InterruptedException e) {
				return null;
			}

			return "Toooooo long";
		}, Duration.ofSeconds(1), "Hello"))
		            .expectSubscription()
		            .expectNext("Hello")
		            .expectComplete()
		            .verify(Duration.ofSeconds(2));
	}
}