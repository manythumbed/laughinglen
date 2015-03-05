package laughinglen.control;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;
import static laughinglen.control.State.latest;

public class HierarchicalControl implements Control {
	public HierarchicalControl(final Control parent, final Control child) {
		this.parent = checkNotNull(parent);
		this.child = checkNotNull(child);
	}

	@Override
	public Observable<State> changes() {
		return Observable.merge(parent.changes(), child.changes()).map(state -> current());
	}

	@Override
	public State current() {
		final State parentState = parent.current();
		final State childState = child.current();
		return new State(parentState.active && childState.active, latest(parentState, childState).observedAt);
	}

	private final Control parent;
	private final Control child;
}
