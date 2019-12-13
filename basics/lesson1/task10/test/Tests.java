import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Tests {

	@Test
	public void testSolution() {
		Flux<Long> sequence = Task.createSequence();

		StepVerifier.create(sequence)
		            .recordWith(ArrayList::new)
		            .expectNextCount(20)
		            .consumeRecordedWith(r -> {
			            Assertions.assertThat(r)
			                      .hasSize(20);
			            Iterator<Long> iterator = r.iterator();
			            for (int i = 0; i < 20; i++) {
				            Assertions.assertThat(iterator.next())
				                      .isEqualTo(SEQUENCE[i]);
			            }
		            })
		            .expectComplete()
		            .verify(Duration.ofMillis(100));
	}

	static final long[] SEQUENCE =
			{0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584,
					4181};
}