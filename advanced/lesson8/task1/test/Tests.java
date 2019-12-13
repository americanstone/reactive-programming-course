import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import reactor.util.context.Context;

public class Tests {

	@Test
	public void testSolution() {
		StepVerifier.create(Task.grabDataFromTheGivenContext("Test")
		                        .subscriberContext(Context.of("Test", "Test")))
		            .expectSubscription()
		            .expectNext("Test")
		            .verifyComplete();
	}
}