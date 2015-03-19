package laughinglen.domain.store;

import laughinglen.domain.Event;
import laughinglen.domain.Id;
import laughinglen.domain.Notifier;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public final class NotifyingStore implements Store {
	private final Store store;
	private final Notifier notifier;

	public NotifyingStore(final Store store, final Notifier notifier) {
		this.store = checkNotNull(store);
		this.notifier = checkNotNull(notifier);
	}


	@Override
	public EventStream stream(final Id id) {
		return store.stream(id);
	}

	@Override
	public EventStream stream(final Id id, final Version version) {
		return store.stream(id, version);
	}

	@Override
	public Status store(final Id id, final Version version, final List<Event> events) {
		final Status status = store.store(id, version, events);
		if(status.succeeded)	{
			for(final Event event : events)	{
				notifier.notify(id, event);
			}
		}

		return status;
	}
}
