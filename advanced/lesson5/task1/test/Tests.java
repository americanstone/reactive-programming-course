import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.DirectProcessor;
import reactor.test.StepVerifier;

public class Tests {

	@Test
	public void mergeSeveralSourcesTest() {
		DirectProcessor<String> processor = DirectProcessor.create();
		StepVerifier
				.create(Task.dropElementsOnBackpressure(processor), 0)
				.expectSubscription()
				.then(() -> processor.onNext(""))
				.then(() -> processor.onNext(""))
				.thenRequest(1)
				.then(() -> processor.onNext("0"))
				.expectNext("0")
				.then(() -> processor.onNext("0"))
				.then(() -> processor.onNext("0"))
				.thenRequest(1)
				.then(() -> processor.onNext("10"))
				.expectNext("10")
				.thenRequest(1)
				.then(() -> processor.onNext("20"))
				.expectNext("20")
				.then(() -> processor.onNext("40"))
				.then(() -> processor.onNext("30"))
				.then(processor::onComplete)
				.expectComplete()
				.verifyThenAssertThat()
				.hasDiscarded("0", "0", "40", "30");
	}
}