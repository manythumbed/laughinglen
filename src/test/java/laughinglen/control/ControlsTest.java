package laughinglen.control;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ControlsTest {

	@Test
	public void shouldAlwaysBeActive() {
		final Control control = Controls.active();

		assertThat(control.current().active).isTrue();

		final Flags flags = new Flags();
		control.changes().subscribe(
				state -> flags.received = true,
				throwable -> flags.errors = true,
				() -> flags.completed = true
		);

		assertThat(flags.received).isFalse();
		assertThat(flags.errors).isFalse();
		assertThat(flags.completed).isTrue();
	}

	@Test
	public void shouldAlwaysBeInactive() {
		final Control control = Controls.inactive();

		assertThat(control.current().active).isFalse();

		final Flags flags = new Flags();
		control.changes().subscribe(
				state -> flags.received = true,
				throwable -> flags.errors = true,
				() -> flags.completed = true
		);

		assertThat(flags.received).isFalse();
		assertThat(flags.errors).isFalse();
		assertThat(flags.completed).isTrue();
	}

	private class Flags {
		boolean received;
		boolean errors;
		boolean completed;
	}
}