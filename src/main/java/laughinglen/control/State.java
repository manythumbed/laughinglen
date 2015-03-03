package laughinglen.control;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.google.common.base.Preconditions.checkNotNull;

public class State {
	public final boolean active;
	public final ZonedDateTime observedAt;

	public State(final boolean active, @Nonnull final ZonedDateTime observedAt) {
		this.active = active;
		this.observedAt = checkNotNull(observedAt);
	}

	public static State from(final boolean active, final long timestamp)	{
		return new State(active, ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("UTC")));
	}

	public static State latest(final State a, final State b)	{
		if(a.observedAt.isBefore(b.observedAt))	{
			return b;
		}

		return a;
	}
}
