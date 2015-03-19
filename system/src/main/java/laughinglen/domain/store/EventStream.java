package laughinglen.domain.store;

import com.google.common.collect.ImmutableList;
import laughinglen.domain.Event;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public final class EventStream {
	public final Version version;
	public final List<Event> events;

	public EventStream(final Version version, final List<Event> events) {
		this.version = checkNotNull(version);
		this.events = ImmutableList.copyOf(events);
	}

	public boolean exists()	{
		return !version.check(0l);
	}

	public static EventStream empty()	{
		return new EventStream(Version.ZERO, Collections.emptyList());
	}
}
