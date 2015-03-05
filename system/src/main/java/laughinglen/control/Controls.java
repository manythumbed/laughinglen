package laughinglen.control;

import rx.Observable;

import java.time.Instant;

public class Controls {

	public static Control active()	{
		return new ConstantControl(true);
	}

	public static Control inactive()	{
		return new ConstantControl(false);
	}

	private static class ConstantControl implements Control	{
		private final boolean value;

		ConstantControl(final boolean value) {
			this.value = value;
		}

		@Override
		public Observable<State> changes() {
			return Observable.empty();
		}

		@Override
		public State current() {
			return State.from(value, Instant.now().toEpochMilli());
		}
	}
}
