import java.util.Arrays;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuples;

public class Tests {

	@Test
	public void testSolution() {
		StepVerifier.create(Flux.from(Task.groupWordsByFirstLatter(Flux.just("ABC",
				"BCD",
				"CDE",
				"BEF",
				"ADE",
				"CFG")))
		                        .flatMap(gf -> gf.collectList()
		                                         .map(l -> Tuples.of(gf.key(), l))))
		            .expectSubscription()
		            .expectNext(Tuples.of('A', Arrays.asList("ABC", "ADE")))
		            .expectNext(Tuples.of('B', Arrays.asList("BCD", "BEF")))
		            .expectNext(Tuples.of('C', Arrays.asList("CDE", "CFG")))
		            .verifyComplete();
	}
}