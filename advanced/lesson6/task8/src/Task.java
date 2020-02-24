import java.time.Duration;
import java.util.Objects;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.retry.Backoff;
import reactor.retry.Retry;

public class Task {

	static final int RETRY_COUNT = 5;
	static final String IF_MESSAGE_STARTS_WITH = "[Retry]";


	public static Publisher<String> retryWithBackoffOnError(Flux<String> publisher) {
		return publisher
				.retryWhen(
					Retry
						.onlyIf(rc -> {
							Throwable exception = rc.exception();
							String message = exception.getMessage();
							return message != null && message.startsWith(IF_MESSAGE_STARTS_WITH);
						})
						.retryMax(RETRY_COUNT)
						.exponentialBackoff(Duration.ofMillis(100), Duration.ofMillis(1600))
				);
	}
}