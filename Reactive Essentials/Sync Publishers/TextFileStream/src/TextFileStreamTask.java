import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;

public class TextFileStreamTask {

	public static Flux<String> readFile(String filename) {
		return Flux.using(
			() -> Files.lines(Paths.get(filename)),
			Flux::fromStream,
			Stream::close
		);
	}
}