package laughinglen.domain;

import laughinglen.domain.store.EventStream;
import laughinglen.domain.store.Status;
import laughinglen.domain.store.Store;

import java.util.Optional;

public abstract class Repository<T extends Root, I extends Id>	{
	private final Store store;

	protected Repository(final Store store) {
		this.store = store;
	}

	public final Optional<T> fetch(final I id)	{
		final EventStream stream = store.stream(id);
		if(!stream.exists())	{
			return Optional.empty();
		}

		final T fetched = instance();
		fetched.update(stream);
		return Optional.of(fetched);
	}

	public final boolean save(final Id id, final T root)	{
		final Status status = store.store(id, root.version, root.changes);

		return status.succeeded;
	}

	abstract protected T instance();
}
