package laughinglen.domain.testing;

import laughinglen.domain.Repository;
import laughinglen.domain.store.Store;

public class TestRepository extends Repository<TestRoot, TestId> {
	public TestRepository(final Store store) {
		super(store);
	}

	@Override
	protected TestRoot instance() {
		return new TestRoot();
	}
}
