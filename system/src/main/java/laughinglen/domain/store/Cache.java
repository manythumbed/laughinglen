package laughinglen.domain.store;

import laughinglen.domain.Id;
import laughinglen.domain.Root;

import java.util.Optional;

public interface Cache<T extends Root, I extends Id> {
	Optional<Snapshot<T>> fetch(I id);

	void store(I id, Snapshot<T> snapshot);
}
