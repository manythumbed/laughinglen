package laughinglen.domain.store.memory;

import com.google.common.collect.Maps;
import laughinglen.domain.Id;
import laughinglen.domain.Root;
import laughinglen.domain.store.Cache;
import laughinglen.domain.store.Snapshot;

import java.util.Map;
import java.util.Optional;

public final class MemoryCache<T extends Root, I extends Id> implements Cache<T, I> {
	private final Map<I, Snapshot<T>> map = Maps.newConcurrentMap();

	@Override
	public Optional<Snapshot<T>> fetch(final I id) {
		return Optional.ofNullable(map.get(id));
	}

	@Override
	public void store(final I id, final Snapshot<T> snapshot) {
		map.put(id, snapshot);
	}
}
