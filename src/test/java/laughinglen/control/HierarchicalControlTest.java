package laughinglen.control;

import com.google.common.collect.Lists;
import org.junit.Test;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

import java.time.Instant;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class HierarchicalControlTest {

	@Test
	public void shouldCombineControls()	{
		final TestControl parent = new TestControl(false);
		final TestControl child = new TestControl(false);
		final HierarchicalControl control = new HierarchicalControl(parent, child);

		final State currentState = control.current();
		assertThat(currentState.active, is(parent.state && child.state));

		final ArrayList<State> changes = Lists.newArrayList();
		final Subscription subscription = control.changes().subscribe(changes::add);

		parent.change(true);
		child.change(true);
		parent.change(false);
		child.change(false);

		subscription.unsubscribe();

		assertThat(changes.size(), is(4));
		assertThat(changes.get(0).active, is(false));
		assertThat(changes.get(1).active, is(true));
		assertThat(changes.get(2).active, is(false));
		assertThat(changes.get(2).active, is(false));
	}

	private class TestControl implements Control {
		private boolean state;
		private StateChanged callback;


		private TestControl(final boolean state) {
			this.state = state;
		}

		@Override
		public Observable<State> changes() {
			return Observable.create(subscriber -> {
				subscriber.add(Subscriptions.create(() -> callback = null));
				callback = s -> subscriber.onNext(State.from(s, timestamp()));
			});
		}

		@Override
		public State current() {
			return State.from(state, timestamp());
		}

		private long timestamp() {
			return Instant.now().toEpochMilli();
		}

		void change(final boolean state) {
			this.state = state;
			if (callback != null) {
				callback.changed(state);
			}
		}
	}

	private interface StateChanged {
		void changed(boolean state);
	}
}