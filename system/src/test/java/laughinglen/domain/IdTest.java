package laughinglen.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IdTest {

	@Test
	public void shouldNotCreateIdFromNullOrEmptyString()	{
		assertThatThrownBy(() -> new TestId(null)).isInstanceOf(NullPointerException.class);
		assertThatThrownBy(() -> new TestId("  \t\n  \t  ")).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void shouldProvideIdValue()	{
		final String value = "id";
		final TestId id = new TestId(value);
		assertThat(id).isNotNull();
		assertThat(id.value).isEqualTo(value);
	}

	private class TestId extends Id	{
		TestId(final String value) {
			super(value);
		}
	}
}