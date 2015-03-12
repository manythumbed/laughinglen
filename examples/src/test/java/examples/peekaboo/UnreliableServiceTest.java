package examples.peekaboo;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnreliableServiceTest {

	@Test
	public void shouldWorkReliably()	{
		final String check = "valid";
		final Service service = new UnreliableService(check);

		assertThat(service.check(new PunchCard.Builder("wrong").build())).isFalse();
		assertThat(service.check(new PunchCard.Builder("wrong").fold().build())).isFalse();
		assertThat(service.check(new PunchCard.Builder(check).build())).isTrue();
		assertThat(service.check(new PunchCard.Builder(check).fold().build())).isFalse();
		assertThat(service.check(new PunchCard.Builder(check).spindle().build())).isFalse();
		assertThat(service.check(new PunchCard.Builder(check).mutilate().build())).isFalse();
	}
}