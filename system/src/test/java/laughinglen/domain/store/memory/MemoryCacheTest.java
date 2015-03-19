package laughinglen.domain.store.memory;

import laughinglen.domain.store.Cache;
import laughinglen.domain.store.Snapshot;
import laughinglen.domain.store.Version;
import laughinglen.domain.testing.TestId;
import laughinglen.domain.testing.TestRoot;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class MemoryCacheTest {
	@Test
	public void shouldStoreAndRetrieveSnapshots() {
		final Cache<TestRoot, TestId> cache = new MemoryCache<>();
		final TestId id = new TestId("123");

		final Optional<Snapshot<TestRoot>> fetch = cache.fetch(id);
		assertThat(fetch).isNotNull();
		assertThat(fetch.isPresent()).isFalse();

		final TestRoot root = new TestRoot();
		cache.store(id, new Snapshot<>(root, new Version(1l)));

		final Optional<Snapshot<TestRoot>> stored = cache.fetch(id);
		assertThat(stored).isNotNull();
		assertThat(stored.isPresent()).isTrue();
		assertThat(stored.get().version).isEqualTo(new Version(1l));
		assertThat(stored.get().value.count()).isEqualTo(root.count());
	}
}