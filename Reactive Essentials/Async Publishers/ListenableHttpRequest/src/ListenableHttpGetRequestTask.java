import java.util.stream.Stream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ListenableHttpGetRequestTask {

	static AsyncHttpClient asyncHttpClient;

	public static Mono<String> getLorem() {
		return Mono.create(sink -> {
			ListenableFuture<String> future = asyncHttpClient.getForObject("https://baconipsum.com/api/?type=meat-and-filler", String.class);
			future.handle((value, error) -> {
				if (error != null) {
					sink.error(error);
					return;
				}

				sink.success(value);
			});
			sink.onCancel(() -> future.cancel());
		});
	}
}