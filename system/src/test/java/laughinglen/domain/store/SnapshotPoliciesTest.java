package laughinglen.domain.store;

import org.junit.Test;

import java.util.Random;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.*;

public class SnapshotPoliciesTest {

	private final Random random = new Random();
	private final LongStream longs = random.longs(0, Long.MAX_VALUE);

	@Test
	public void shouldNeverSnapshot() {
		final SnapshotPolicy never = SnapshotPolicies.never();

		assertThat(never.snapshot(Version.ZERO)).isFalse();
		assertThat(never.snapshot(new Version(1l))).isFalse();
		assertThat(never.snapshot(new Version(longs.findFirst().getAsLong()))).isFalse();
	}

	@Test
	public void shouldAlwaysSnapshot() {
		final SnapshotPolicy always = SnapshotPolicies.always();

		assertThat(always.snapshot(Version.ZERO)).isTrue();
		assertThat(always.snapshot(new Version(1l))).isTrue();
		assertThat(always.snapshot(new Version(longs.findFirst().getAsLong()))).isTrue();
	}

	@Test
	public void shouldSnapshotOnInterval()	{
		final SnapshotPolicy interval = SnapshotPolicies.every(7);

		assertThat(interval.snapshot(Version.ZERO)).isFalse();
		assertThat(interval.snapshot(new Version(6))).isFalse();
		assertThat(interval.snapshot(new Version(7))).isTrue();
		assertThat(interval.snapshot(new Version(8))).isFalse();
		assertThat(interval.snapshot(new Version(14))).isTrue();
		assertThat(interval.snapshot(new Version(15))).isFalse();
	}
}