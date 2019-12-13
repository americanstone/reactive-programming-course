import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Tests {

	@Test
	public void findVideoTest() {
		PaymentService service = new PaymentService();

		StepVerifier.create(service.findPayments(Flux.range(1, 100)
		                                             .map(String::valueOf))
		                           .then())
		            .expectSubscription()
		            .verifyComplete();
	}
}