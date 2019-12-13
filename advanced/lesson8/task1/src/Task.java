import reactor.core.publisher.Mono;

public class Task {

	public static Mono<String> grabDataFromTheGivenContext(Object key) {
		return Mono.subscriberContext()
		           .handle((c, ss) -> {
			           if (c.hasKey(key)) {
				           String value = c.get(key);
				           ss.next(value);
			           }
		           });
	}
}