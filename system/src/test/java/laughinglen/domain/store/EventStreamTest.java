package laughinglen.domain.store;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class EventStreamTest {
	@Test
	public void shouldNotCreateEventStreamFromNullVersion()	{
		assertThatThrownBy(() -> new EventStream(null, null)).isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldCreateEventStream()	{
		final Version version = new Version(1);
		final EventStream eventStream = new EventStream(version, null);

		assertThat(eventStream.events).isNotNull().isEmpty();
		assertThat(eventStream.version).isEqualTo(version);
		assertThat(eventStream.exists()).isFalse();
	}
}