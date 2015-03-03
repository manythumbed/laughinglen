package laughinglen.control;

import rx.Observable;

public interface Control {
	Observable<State> changes();
	State current();
}
