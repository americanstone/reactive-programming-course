import java.util.function.BiConsumer;
import java.util.function.Function;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

public class Task {

	public static Publisher<Integer> provideHandmadeContinuation(Flux<Integer> values,
			Function<Integer,
			Integer> mapping) {

		return values
				.handle(new BiConsumer<Integer, SynchronousSink<Integer>>() {
					@Override
					public void accept(Integer integer, SynchronousSink<Integer> integerSynchronousSink) {
						try {
							final Integer mappingResult = mapping.apply(integer);
							integerSynchronousSink.next(mappingResult);
						} catch (Exception e) {
							// ignore
						}
					}
				});
	}
}