package laughinglen.control;

import com.google.common.base.Strings;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;
import rx.Observable;
import rx.subscriptions.Subscriptions;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class ArchaiusControl implements Control {
	public ArchaiusControl(final String property, final DynamicPropertyFactory propertyFactory) {
		checkArgument(!Strings.isNullOrEmpty(property));
		this.property = property;
		this.propertyFactory = checkNotNull(propertyFactory);
	}

	@Override
	public Observable<State> changes() {
		return Observable.create(observer -> {
			final DynamicBooleanProperty control = control();

			observer.add(Subscriptions.create(control::removeAllCallbacks));

			control.addCallback(() -> {
				if (!observer.isUnsubscribed()) {
					observer.onNext(fromControl(control));
				}
			});
		});
	}

	@Override
	public State current() {
		return fromControl(control());
	}

	private DynamicBooleanProperty control() {
		return propertyFactory.getBooleanProperty(property, false);
	}

	private State fromControl(final DynamicBooleanProperty control) {
		return State.from(control.get(), control.getChangedTimestamp());
	}

	private final DynamicPropertyFactory propertyFactory;
	private final String property;
}
