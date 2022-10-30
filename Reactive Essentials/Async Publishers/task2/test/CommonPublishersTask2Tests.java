import java.time.Duration;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class CommonPublishersTask2Tests {

	@Test
	public void testSolution() {
		Task.uuidGenerator = Mockito.mock(UUIDGenerator.class);
		Mockito.when(Task.uuidGenerator.secureUUID()).thenReturn("test-uuid");

		Object sequence = Task.generateRandomUUID();

		Mockito.verify(Task.uuidGenerator, Mockito.times(0)).secureUUID();

		if (!(sequence instanceof Mono)) {
			Assertions.fail("You need to refactor API so it uses Reactive API properly");
		}

		StepVerifier.create(((Mono<String>) sequence))
					.expectNext("test-uuid")
		            .expectComplete()
		            .verify(Duration.ofMillis(100));
	}
}