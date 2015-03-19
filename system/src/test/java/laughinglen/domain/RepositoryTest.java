package laughinglen.domain;

import com.google.common.collect.Lists;
import laughinglen.domain.store.Cache;
import laughinglen.domain.store.Snapshot;
import laughinglen.domain.store.Store;
import laughinglen.domain.store.Version;
import laughinglen.domain.store.memory.MemoryCache;
import laughinglen.domain.store.memory.MemoryStore;
import laughinglen.domain.testing.TestEvent;
import laughinglen.domain.testing.TestId;
import laughinglen.domain.testing.TestRepository;
import laughinglen.domain.testing.TestRoot;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class RepositoryTest {
	private Store store;
	private Cache<TestRoot, TestId> cache;
	private Repository<TestRoot, TestId> repository;

	@Before
	public void setUp()	{
		store = new MemoryStore();
		cache = new MemoryCache<>();
		repository = new TestRepository(store, cache);
	}

	@Test
	public void shouldStoreRoot() {
		final TestRoot root = new TestRoot();
		final TestId id = new TestId("1");
		assertThat(repository.save(id, root)).isFalse();

		root.increment();
		assertThat(repository.save(id, root)).isTrue();

		final Optional<TestRoot> fetched = repository.fetch(id);
		assertThat(fetched.isPresent()).isTrue();
		assertThat(fetched.get().count()).isEqualTo(root.count());

		fetched.get().bigIncrement();
		assertThat(repository.save(id, fetched.get())).isTrue();

		assertThat(repository.save(id, root)).isFalse();
	}

	@Test
	public void shouldIgnoreUnknownEvents() {
		final TestId id = new TestId("1");
		final Event bad = new BadEvent();

		assertThat(store.store(id, Version.ZERO, Lists.newArrayList(bad, bad, bad, bad)).succeeded).isTrue();

		final Optional<TestRoot> fetched = repository.fetch(id);
		assertThat(fetched.isPresent()).isTrue();
		assertThat(fetched.get().count()).isEqualTo(0);

		fetched.get().increment();
		assertThat(fetched.get().count()).isEqualTo(1);
		assertThat(repository.save(id, fetched.get())).isTrue();
	}

	@Test
	public void shouldUseCachedValueIfAvailable()	{
		final TestId id = new TestId("1");
		final TestEvent event = new TestEvent();

		assertThat(store.store(id, Version.ZERO, Lists.newArrayList(event, event, event)).succeeded).isTrue();

		final Optional<TestRoot> original = repository.fetch(id);
		assertThat(original).isNotNull();
		assertThat(original.isPresent()).isTrue();
		assertThat(original.get().count()).isEqualTo(3);

		cache.store(id, new Snapshot<>(original.get(), new Version(3)));
		assertThat(store.store(id, new Version(3), Lists.newArrayList(event, event)).succeeded).isTrue();

		final Optional<TestRoot> cached = repository.fetch(id);
		assertThat(cached).isNotNull();
		assertThat(cached.isPresent()).isTrue();
		assertThat(cached.get().count()).isEqualTo(5);
	}

	private class BadEvent extends Event {
	}
}