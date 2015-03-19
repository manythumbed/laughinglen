package laughinglen.domain;

import com.google.common.collect.Lists;
import laughinglen.domain.store.Store;
import laughinglen.domain.store.Version;
import laughinglen.domain.store.memory.MemoryStore;
import laughinglen.domain.testing.TestId;
import laughinglen.domain.testing.TestRepository;
import laughinglen.domain.testing.TestRoot;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class RepositoryTest {
	private final Store store = new MemoryStore();
	private final Repository<TestRoot, TestId> repository = new TestRepository(store);

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

	private class BadEvent extends Event {
	}
}