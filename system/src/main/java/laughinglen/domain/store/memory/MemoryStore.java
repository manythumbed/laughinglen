package laughinglen.domain.store.memory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import laughinglen.domain.Event;
import laughinglen.domain.Id;
import laughinglen.domain.store.EventStream;
import laughinglen.domain.store.Status;
import laughinglen.domain.store.Store;
import laughinglen.domain.store.Version;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class MemoryStore implements Store {
	private final Map<Id, List<Event>> map = Collections.synchronizedMap(Maps.newHashMap());

	@Override
	public EventStream stream(final Id id) {
		return stream(id, new Version(0));
	}

	@Override
	public EventStream stream(final Id id, final Version version) {
		final List<Event> events = map.get(id);
		if(events == null)	{
			return EventStream.empty();
		}

		if(version.value < events.size())	{
			return new EventStream(new Version(events.size()), events.subList((int) version.value, events.size()));
		}

		return new EventStream(new Version(events.size()), Collections.emptyList());
	}

	@Override
	public Status store(final Id id, final Version version, final List<Event> events) {
		if (events == null || events.isEmpty()) {
			return Status.failed(Version.ZERO);
		}

		final List<Event> found = map.get(id);
		if (found == null) {
			if (!version.check(0l)) {
				return Status.failed(Version.ZERO);
			}
			map.put(id, events);
			return Status.succeeded(new Version(events.size()));
		}

		if (!version.check(found.size())) {
			return Status.failed(new Version(found.size()));
		}

		final ArrayList<Event> updated = Lists.newArrayList(found);
		updated.addAll(events);
		map.put(id, updated);

		return Status.succeeded(new Version(updated.size()));
	}
}
