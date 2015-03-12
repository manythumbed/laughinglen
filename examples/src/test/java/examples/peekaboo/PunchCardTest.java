package examples.peekaboo;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class PunchCardTest {
	@Test
	public void shouldBuildPunchCard()	{
		final String content = "content";

		final PunchCard card = new PunchCard.Builder(content).build();
		assertThat(card.content).isEqualTo(content);
		assertThat(card.folded).isFalse();
		assertThat(card.spindled).isFalse();
		assertThat(card.mutilated).isFalse();

		final PunchCard badCard = new PunchCard.Builder(content).fold().spindle().mutilate().build();
		assertThat(badCard.content).isEqualTo(content);
		assertThat(badCard.folded).isTrue();
		assertThat(badCard.spindled).isTrue();
		assertThat(badCard.mutilated).isTrue();
	}

}