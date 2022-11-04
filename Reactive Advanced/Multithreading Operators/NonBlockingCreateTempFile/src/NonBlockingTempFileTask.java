
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;

public class NonBlockingTempFileTask {

	static Files files;

	public static Mono<File> createTempFile(String prefix, String suffix) {
		return Mono.fromCallable(() -> files.createTempFile(prefix, suffix))
				.subscribeOn(Schedulers.boundedElastic());
	}
}