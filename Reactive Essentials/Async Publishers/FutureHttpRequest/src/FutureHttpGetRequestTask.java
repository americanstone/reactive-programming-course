import reactor.core.publisher.Mono;
import java.util.concurrent.CompletableFuture;

public class FutureHttpGetRequestTask {

	static AsyncRestTemplate asyncRestTemplate;

	public static Mono<String> getLorem() {
		return Mono.fromFuture(asyncRestTemplate.getForObject("https://baconipsum.com/api/?type=meat-and-filler", String.class));
	}
}