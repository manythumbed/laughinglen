package laughinglen.control;

import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static laughinglen.control.State.latest;
import static org.assertj.core.api.Assertions.*;

public class StateTest {

	@Test
	public void shouldProvideLatestState()	{
		final ZonedDateTime referenceTime = ZonedDateTime.now(ZoneId.of("UTC"));
		final State a = new State(true, referenceTime);
		final State b = new State(true, referenceTime.minusDays(1));

		assertThat(latest(a, a).observedAt).isEqualTo(a.observedAt);
		assertThat(latest(b, b).observedAt).isEqualTo(b.observedAt);
		assertThat(latest(a, b).observedAt).isEqualTo(a.observedAt);
		assertThat(latest(b, a).observedAt).isEqualTo(a.observedAt);
	}

}