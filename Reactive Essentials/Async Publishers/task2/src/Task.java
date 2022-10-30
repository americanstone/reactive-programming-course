import reactor.core.publisher.Mono;

public class Task {

	static UUIDGenerator uuidGenerator;

	public static Mono<String> generateRandomUUID() {
		return Mono.defer(() -> Mono.just(uuidGenerator.secureUUID()));
	}
}