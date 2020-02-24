import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.UnicastProcessor;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

public class Tests {

	@Test
	public void testSolution() {
		TestPublisher<String> publisher = TestPublisher.create();

		StepVerifier
			.withVirtualTime(() -> Task.keepBackpressureForLongRunningOps(
				publisher.flux(), q -> {
						UnicastProcessor<StatisticSnapshot> sProducer3 = UnicastProcessor.create();
						return sProducer3.doOnRequest(r -> {
							sProducer3.onNext(new TestStatistic(q));
							sProducer3.onComplete();
						}).next();
					}), 0)
			.expectSubscription()
			.then(() -> {
				publisher.next("test0");
				publisher.next("test1");
				publisher.next("test2");
				publisher.next("test3");
				publisher.next("test4");
			})
			.thenRequest(1)
			.expectNext(new TestStatistic("test0"))
			.thenRequest(1)
			.expectNext(new TestStatistic("test1"))
			.then(() -> publisher.next("test5"))
			.then(() -> publisher.next("test6"))
			.thenRequest(1)
			.expectNext(new TestStatistic("test4"))
			.thenRequest(1)
			.expectNext(new TestStatistic("test5"))
			.thenCancel()
			.verify();
	}

	static class TestStatistic implements StatisticSnapshot {

		final String query;

		TestStatistic(String query) {
			this.query = query;
		}

		@Override
		public int hashCode() {
			return query.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			TestStatistic statistic = (TestStatistic) o;

			return query.equals(statistic.query);
		}

		@Override
		public String toString() {
			return "TestStatistic{" + "query='" + query + '\'' + '}';
		}
	}
}