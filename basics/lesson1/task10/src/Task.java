import reactor.core.publisher.Flux;

public class Task {

	public static Flux<Long> createSequence() {
		return Flux.<Long, State>generate(() -> STATE_ONE_2, ((state, sink) -> {
			State next = new State(state.iteration + 1,
					state.value + state.previous.value,
					state);
			sink.next(next.value);

			if (next.iteration == 19) {
				sink.complete();
			}

			return next;
		})).startWith(STATE_ZERO.value, STATE_ONE_1.value, STATE_ONE_2.value);
	}

	static class State {

		final State previous;
		final long  value;
		final long  iteration;

		State(long iteration, long value, State previous) {
			this.iteration = iteration;
			this.previous = previous;
			this.value = value;
		}
	}

	static final State STATE_ZERO  = new State(0, 0, null);
	static final State STATE_ONE_1 = new State(1, 1, STATE_ZERO);
	static final State STATE_ONE_2 = new State(2, 1, STATE_ONE_1);
}