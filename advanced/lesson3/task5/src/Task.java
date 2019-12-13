import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

public class Task {

	public static Publisher<GroupedFlux<Character, String>> groupWordsByFirstLatter(Flux<String> words) {
		return words.groupBy(s -> s.charAt(0));
	}
}