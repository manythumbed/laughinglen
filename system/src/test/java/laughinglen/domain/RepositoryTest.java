package laughinglen.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class RepositoryTest {
	private Store store;
	private WrappedCache<TestRoot, TestId> cache;
	private Repository<TestRoot, TestId> repository;

	@Before
	public void setUp()	{
		store = new MemoryStore();
		cache = new WrappedCache<>(new MemoryCache<>());
		repository = new TestRepository(store, cache);
	}

	@Test
	public void shouldReturnAbsentIFNotFound()	{
		final Optional<TestRoot> fetched = repository.fetch(new TestId("1"));
		assertThat(fetched.isPresent()).isFalse();
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
		assertThat(cache.hits.get(id)).isEqualTo(1);
		assertThat(cached).isNotNull();
		assertThat(cached.isPresent()).isTrue();
		assertThat(cached.get().count()).isEqualTo(5);
	}

	@Test
	public void shouldIncrementVersionCorrectly()	{
		final TestRoot root = new TestRoot();
		final TestId id = new TestId("1");
		assertThat(repository.save(id, root)).isFalse();

		root.increment();
		assertThat(repository.save(id, root)).isTrue();

		root.increment();
		assertThat(repository.save(id, root)).isTrue();
		assertThat(repository.save(id, root)).isFalse();

	}

	private class WrappedCache<T extends Root, I extends Id> implements Cache<T, I>	{
		private final Cache<T, I> cache;
		final Map<Id, Integer> hits = Maps.newHashMap();

		private WrappedCache(final Cache<T, I> cache) {
			this.cache = cache;
		}

		@Override
		public Optional<Snapshot<T>> fetch(final I id) {
			final Optional<Snapshot<T>> fetch = cache.fetch(id);
			if(fetch.isPresent())	{
				hits.merge(id, 1, (v, n) -> v + n );
			}
			return fetch;
		}

		@Override
		public void store(final I id, final Snapshot<T> snapshot) {
			cache.store(id, snapshot);
		}
	}

	private class BadEvent extends Event {
	}
}