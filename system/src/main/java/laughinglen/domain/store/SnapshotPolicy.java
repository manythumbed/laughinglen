package laughinglen.domain.store;

import static com.google.common.base.Preconditions.checkArgument;

@FunctionalInterface
public interface SnapshotPolicy	{
	boolean snapshot(Version version);

	static SnapshotPolicy never()	{
		return version -> false;
	}

	static SnapshotPolicy always()	{
		return version -> true;
	}

	static SnapshotPolicy every(final int interval)	{
		checkArgument(interval >= 1);
		return version -> version.value != 0 && version.value % interval == 0;
	}
}
