package laughinglen.domain.store;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class VersionTest {

	@Test
	public void shouldNotCreateWithNegativeValue()	{
		assertThatThrownBy(() -> {new Version(-100l);}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void shouldHaveZeroAsZeroValue()	{
		assertThat(Version.ZERO.value).isEqualTo(0l);
	}

	@Test
	public void shouldImplementEqualsCorrectly()	{
		final Version a = new Version(1);
		final Version b = new Version(2);
		final Version c = new Version(1);

		assertThat(a.equals(a)).isTrue();
		assertThat(a.equals(null)).isFalse();
		assertThat(a.equals("not a version")).isFalse();
		assertThat(a.equals(b)).isFalse();
		assertThat(b.equals(a)).isFalse();
		assertThat(a.equals(c)).isTrue();
		assertThat(c.equals(a)).isTrue();
	}

	@Test
	public void shouldImplementHashCode()	{
		final Version a = new Version(1);
		final Version b = new Version(2);
		final Version c = new Version(1);

		assertThat(a.hashCode() == b.hashCode()).isFalse();
		assertThat(b.hashCode() == a.hashCode()).isFalse();
		assertThat(a.hashCode() == a.hashCode()).isTrue();
		assertThat(a.hashCode() == c.hashCode()).isTrue();
		assertThat(c.hashCode() == a.hashCode()).isTrue();
	}
}