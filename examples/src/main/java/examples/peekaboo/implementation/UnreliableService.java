package examples.peekaboo.implementation;

import examples.peekaboo.PunchCard;
import examples.peekaboo.Service;

final class UnreliableService implements Service {
	private final String valid;

	UnreliableService(final String valid) {
		this.valid = valid;
	}

	@Override
	public boolean check(final PunchCard punchCard) {
		return !(punchCard.folded || punchCard.spindled || punchCard.mutilated) && valid.equals(punchCard.content);
	}
}
