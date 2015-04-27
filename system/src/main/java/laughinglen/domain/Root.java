package laughinglen.domain;

import com.google.common.collect.Lists;
import laughinglen.domain.store.EventStream;
import laughinglen.domain.store.Version;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class Root {
	final List<Event> changes = Lists.newArrayList();
	Version version = Version.ZERO;

	final void storeSucceeded(final Version stored)	{
		version = stored;
		changes.clear();
	}

	protected final void update(final EventStream stream) {
		version = stream.version;
		stream.events.forEach(this::apply);
	}

	private void apply(final Event event) {
		try {
			final Method handler = this.getClass().getDeclaredMethod("handle", event.getClass());
			handler.setAccessible(true);

			handler.invoke(this, event);
		}
		catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {
		}
	}

	protected final void record(final Event event)	{
		apply(event);
		changes.add(event);
	}

}
