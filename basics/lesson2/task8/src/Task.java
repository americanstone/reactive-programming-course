import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Task {

	public static Mono<List<String>> createSequence(Flux<String> flux) {
		return flux.collectList();
	}
}