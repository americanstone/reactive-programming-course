import reactor.core.publisher.Flux;

import java.time.Duration;

public class SchedulePeriodicTask {

	public static Flux<Long> executeTaskPeriodically(Runnable task, long periodInMillis) {
		return Flux.interval(Duration.ofMillis(periodInMillis))
				.doOnNext(tick -> task.run());
	}
}