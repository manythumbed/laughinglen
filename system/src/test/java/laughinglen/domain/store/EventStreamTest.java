package laughinglen.domain.store;

import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

public class EventStreamTest {
	@Test
	public void shouldNotCreateEventStreamFromNullVersionOrList()	{
		assertThatThrownBy(() -> new EventStream(null, Collections.emptyList())).isInstanceOf(NullPointerException.class);
		assertThatThrownBy(() -> new EventStream(Version.ZERO, null)).isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldCreateEventStream()	{
		final EventStream eventStream = new EventStream(Version.ZERO, Collections.emptyList());

		assertThat(eventStream.events).isNotNull().isEmpty();
		assertThat(eventStream.version).isEqualTo(Version.ZERO);
		assertThat(eventStream.exists()).isFalse();
	}
}