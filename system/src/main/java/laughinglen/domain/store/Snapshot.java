package laughinglen.domain.store;

import laughinglen.domain.Root;

import static com.google.common.base.Preconditions.checkNotNull;

public class Snapshot<T extends Root> {
	public final T value;
	public final Version version;

	public Snapshot(final T value, final Version version) {
		this.value = checkNotNull(value);
		this.version = checkNotNull(version);
	}
}
