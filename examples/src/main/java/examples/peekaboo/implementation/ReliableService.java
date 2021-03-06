package examples.peekaboo.implementation;

import examples.peekaboo.PunchCard;
import examples.peekaboo.Service;

import static com.google.common.base.Preconditions.checkNotNull;

final class ReliableService implements Service {
	private final String valid;

	ReliableService(final String valid) {
		this.valid = checkNotNull(valid);
	}

	@Override
	public boolean check(final PunchCard punchCard) {
		return valid.equals(punchCard.content);
	}
}
