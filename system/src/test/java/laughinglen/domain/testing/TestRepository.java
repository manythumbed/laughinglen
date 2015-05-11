package laughinglen.domain.testing;

import laughinglen.domain.Repository;
import laughinglen.domain.store.Cache;
import laughinglen.domain.store.SnapshotPolicies;
import laughinglen.domain.store.Store;

public class TestRepository extends Repository<TestRoot, TestId> {
	public TestRepository(final Store store, final Cache<TestRoot, TestId> cache) {
		super(store, cache, SnapshotPolicies.never());
	}

	@Override
	protected TestRoot instance() {
		return new TestRoot();
	}
}
