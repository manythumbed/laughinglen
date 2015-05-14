package laughinglen.domain.testing;

import laughinglen.domain.Repository;
import laughinglen.domain.RepositoryTest;
import laughinglen.domain.store.Cache;
import laughinglen.domain.store.SnapshotPolicies;
import laughinglen.domain.store.SnapshotPolicy;
import laughinglen.domain.store.Store;

public class TestRepository extends Repository<TestRoot, TestId> {
	public TestRepository(final Store store, final Cache<TestRoot, TestId> cache) {
		this(store, cache, SnapshotPolicies.never());
	}

	public TestRepository(final Store store, final Cache<TestRoot, TestId> cache, final SnapshotPolicy policy) {
		super(store, cache, policy);
	}

	@Override
	protected TestRoot instance() {
		return new TestRoot();
	}
}
