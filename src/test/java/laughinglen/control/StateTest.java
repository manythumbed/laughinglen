package laughinglen.control;

import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static laughinglen.control.State.latest;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class StateTest {

	@Test
	public void shouldProvideLatestState()	{
		final ZonedDateTime referenceTime = ZonedDateTime.now(ZoneId.of("UTC"));
		final State a = new State(true, referenceTime);
		final State b = new State(true, referenceTime.minusDays(1));

		assertThat(latest(a, a).observedAt, equalTo(a.observedAt));
		assertThat(latest(b, b).observedAt, equalTo(b.observedAt));
		assertThat(latest(a, b).observedAt, equalTo(a.observedAt));
		assertThat(latest(b, a).observedAt, equalTo(a.observedAt));
	}

}