import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

public class Task {

	public static ParallelFlux<Integer> parallelizeWorkOnDifferentThreads(Flux<Integer> source) {
		return source.parallel()
		             .runOn(Schedulers.parallel());
	}
}