package laughinglen.control;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class State {
	public final boolean active;
	public final ZonedDateTime observedAt;

	public State(final boolean active, @Nonnull final ZonedDateTime observedAt) {
		this.active = active;
		this.observedAt = observedAt;
	}

	public static State from(final boolean active, final long timestamp)	{
		return new State(active, ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("UTC")));
	}
}
