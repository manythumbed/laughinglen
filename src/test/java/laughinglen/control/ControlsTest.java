package laughinglen.control;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ControlsTest {

	@Test
	public void shouldAlwaysBeActive() {
		final Control control = Controls.active();

		assertThat(control.current().active, is(true));

		final Flags flags = new Flags();
		control.changes().subscribe(
				state -> flags.received = true,
				throwable -> flags.errors = true,
				() -> flags.completed = true
		);

		assertThat(flags.received, is(false));
		assertThat(flags.errors, is(false));
		assertThat(flags.completed, is(true));
	}

	@Test
	public void shouldAlwaysBeInactive() {
		final Control control = Controls.inactive();

		assertThat(control.current().active, is(false));

		final Flags flags = new Flags();
		control.changes().subscribe(
				state -> flags.received = true,
				throwable -> flags.errors = true,
				() -> flags.completed = true
		);

		assertThat(flags.received, is(false));
		assertThat(flags.errors, is(false));
		assertThat(flags.completed, is(true));
	}

	private class Flags {
		boolean received;
		boolean errors;
		boolean completed;
	}
}