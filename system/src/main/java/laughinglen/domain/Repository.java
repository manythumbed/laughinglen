package laughinglen.domain;

import laughinglen.domain.store.Cache;
import laughinglen.domain.store.EventStream;
import laughinglen.domain.store.Snapshot;
import laughinglen.domain.store.Status;
import laughinglen.domain.store.Store;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class Repository<T extends Root, I extends Id>	{
	private final Store store;
	private final Cache<T, I> cache;

	protected Repository(final Store store, final Cache<T, I> cache) {
		this.store = checkNotNull(store);
		this.cache = checkNotNull(cache);
	}

	public final Optional<T> fetch(final I id)	{
		final Optional<Snapshot<T>> snapshot = cache.fetch(id);

		final EventStream stream = stream(id, snapshot);
		if(!stream.exists())	{
			return Optional.empty();
		}

		final T fetched = buildInstance(snapshot);
		fetched.update(stream);
		return Optional.of(fetched);
	}

	private T buildInstance(final Optional<Snapshot<T>> snapshot)	{
		if(snapshot.isPresent())	{
			return snapshot.get().value;
		}

		return instance();
	}

	private EventStream stream(final I id, final Optional<Snapshot<T>> snapshot)	{
		if(snapshot.isPresent())	{
			return store.stream(id, snapshot.get().version);
		}

		return store.stream(id);
	}

	public final boolean save(final Id id, final T root)	{
		final Status status = store.store(id, root.version, root.changes);

		return status.succeeded;
	}

	abstract protected T instance();
}
