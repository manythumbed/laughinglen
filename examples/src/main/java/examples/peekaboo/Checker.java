package examples.peekaboo;

import javax.inject.Inject;
import javax.inject.Named;

public final class Checker {
	private final Service reliable;
	private final Service unreliable;

	@Inject
	public Checker(@Named("reliable") final Service reliable, @Named("unreliable") final Service unreliable) {
		this.reliable = reliable;
		this.unreliable = unreliable;
	}

	public boolean check(final PunchCard punchCard, boolean reliably)	{
		if(reliably)	{
			return reliable.check(punchCard);
		}

		return unreliable.check(punchCard);
	}
}
