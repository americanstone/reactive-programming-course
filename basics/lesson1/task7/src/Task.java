import java.util.concurrent.CompletionStage;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

public class Task {

	public static Publisher<String> createSequence(CompletionStage<String> completionStage) {
		return Mono.fromCompletionStage(completionStage);
	}
}