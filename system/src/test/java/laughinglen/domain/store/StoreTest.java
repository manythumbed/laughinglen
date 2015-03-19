package laughinglen.domain.store;

import com.google.common.collect.Lists;
import laughinglen.domain.Event;
import laughinglen.domain.TestId;
import laughinglen.domain.store.memory.MemoryStore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class StoreTest {
	@Test
	public void shouldTestStoreImplementations() {
		checkStore(new MemoryStore());
	}

	private void checkStore(final Store store) {
		missingStreamReturnsEmptyStream(store);
		emptyStreamIsNotStored(store);
		streamIsStored(store);
		incorrectVersionOfStreamIsNotStored(store);
		streamIsStoredAndRetrieved(store);
		partialStreamIsRetrieved(store);
	}

	private void missingStreamReturnsEmptyStream(final Store store) {
		final EventStream missingStream = store.stream(new TestId("missing"));

		assertThat(missingStream).isNotNull();
		assertThat(missingStream.exists()).isFalse();
	}

	private void emptyStreamIsNotStored(final Store store) {
		final Status status = store.store(new TestId("empty"), new Version(1), Lists.newArrayList());

		assertThat(status).isNotNull();
		assertThat(status.succeeded).isFalse();
		assertThat(status.version.value).isEqualTo(0);
	}

	private void streamIsStored(final Store store) {
		final Status status = store.store(new TestId("created"), Version.ZERO, Lists.newArrayList(new TestEvent1(1)));

		assertThat(status).isNotNull();
		assertThat(status.succeeded).isTrue();
		assertThat(status.version.value).isEqualTo(1);
	}

	private void incorrectVersionOfStreamIsNotStored(final Store store) {
		final TestId id = new TestId("incorrect-version");
		final Version expectedVersion = new Version(2l);
		final Status status = store.store(id, Version.ZERO, Lists.newArrayList(new TestEvent1(1), new TestEvent1(2)));

		assertThat(status).isNotNull();
		assertThat(status.succeeded).isTrue();
		assertThat(status.version).isEqualTo(expectedVersion);

		final Status tooHigh = store.store(id, new Version(23l), Lists.newArrayList(new TestEvent1(23), new TestEvent1(24)));

		assertThat(tooHigh.succeeded).isFalse();
		assertThat(tooHigh.version).isEqualTo(expectedVersion);

		final Status tooLow = store.store(id, new Version(1l), Lists.newArrayList(new TestEvent1(230), new TestEvent1(240)));

		assertThat(tooLow.succeeded).isFalse();
		assertThat(tooLow.version).isEqualTo(expectedVersion);

		final Status correct = store.store(id, new Version(2), Lists.newArrayList(new TestEvent1(44)));

		assertThat(correct.succeeded).isTrue();
		assertThat(correct.version).isEqualTo(new Version(3));

	}

	private void streamIsStoredAndRetrieved(final Store store) {
		final TestId id = new TestId("store-retrieve");
		final Status status = store.store(id, Version.ZERO, Lists.newArrayList(new TestEvent1(1), new TestEvent1(2)));

		assertThat(status).isNotNull();
		assertThat(status.succeeded).isTrue();
		assertThat(status.version.value).isEqualTo(2);

		final EventStream stream = store.stream(id);
		assertThat(stream).isNotNull();
		assertThat(stream.exists()).isTrue();
		assertThat(stream.events.size()).isEqualTo(2);
		assertThat(stream.events.get(0)).isInstanceOf(TestEvent1.class);
		assertThat(stream.events.get(1)).isInstanceOf(TestEvent1.class);

		final TestEvent1 e1 = (TestEvent1) stream.events.get(0);
		final TestEvent1 e2 = (TestEvent1) stream.events.get(1);

		assertThat(e1.value).isEqualTo(1);
		assertThat(e2.value).isEqualTo(2);
	}

	private void partialStreamIsRetrieved(final Store store) {
		final TestId id = new TestId("store-partial-retrieve");
		final Status status = store.store(id, Version.ZERO, Lists.newArrayList(new TestEvent1(1), new TestEvent1(2)));

		assertThat(status).isNotNull();
		assertThat(status.succeeded).isTrue();
		assertThat(status.version.value).isEqualTo(2);

		final EventStream missingStream = store.stream(new TestId("missing-partial-stream"), new Version(1));

		assertThat(missingStream).isNotNull();
		assertThat(missingStream.exists()).isFalse();
		assertThat(missingStream.version).isEqualTo(Version.ZERO);
		assertThat(missingStream.events.isEmpty()).isTrue();

		final EventStream partialStream = store.stream(id, new Version(1));

		assertThat(partialStream).isNotNull();
		assertThat(partialStream.exists()).isTrue();
		assertThat(partialStream.version).isEqualTo(new Version(2l));
		assertThat(partialStream.events.size()).isEqualTo(1);
		assertThat(partialStream.events.get(0)).isInstanceOf(TestEvent1.class);

		final TestEvent1 e1 = (TestEvent1) partialStream.events.get(0);

		assertThat(e1.value).isEqualTo(2);

		final EventStream emptyStream = store.stream(id, new Version(2));

		assertThat(emptyStream).isNotNull();
		assertThat(emptyStream.exists()).isTrue();
		assertThat(emptyStream.version).isEqualTo(new Version(2l));
		assertThat(emptyStream.events.isEmpty()).isTrue();
	}

	private class TestEvent1 extends Event {
		public final int value;

		private TestEvent1(final int value) {
			this.value = value;
		}
	}
}