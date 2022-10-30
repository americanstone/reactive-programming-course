import reactor.core.publisher.Mono;

public class HandleGetRequestTask {

	public static final IllegalArgumentException UNSUPPORTED_HTTP_METHOD =
			new IllegalArgumentException("Unsupported HTTP method. Expected method GET");

	public static ReactiveRandom reactiveRandom;

	public static Mono<Integer> getRandomNumberHandler(RequestEntity<?> request) {
		if (request.method != HttpMethod.GET) {
			return Mono.error(UNSUPPORTED_HTTP_METHOD);
		}

		return reactiveRandom.nextInt();
	}
}