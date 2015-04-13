package examples.peekaboo;

import examples.peekaboo.implementation.ServiceModule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;


public class ServiceModuleTest {

	@Test
	public void shouldBeUsableInComponent()	{
		final String check = "check";
		final Checker checker = DaggerTestComponent.builder().serviceModule(new ServiceModule(check)).build().checker();

		final PunchCard wrong = new PunchCard.Builder("wrong").build();
		final PunchCard wrongFolded = new PunchCard.Builder("wrong").fold().build();
		final PunchCard correct = new PunchCard.Builder(check).build();
		final PunchCard correctFolded = new PunchCard.Builder(check).fold().build();

		assertThat(checker.check(wrong, true)).isFalse();
		assertThat(checker.check(correct, true)).isTrue();
		assertThat(checker.check(wrong, false)).isFalse();
		assertThat(checker.check(correct, false)).isTrue();

		assertThat(checker.check(wrongFolded, true)).isFalse();
		assertThat(checker.check(correctFolded, true)).isTrue();
		assertThat(checker.check(wrongFolded, false)).isFalse();
		assertThat(checker.check(correctFolded, false)).isFalse();

	}

}