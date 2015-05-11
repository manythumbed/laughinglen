package laughinglen.domain.store;

import static com.google.common.base.Preconditions.checkArgument;

public final class SnapshotPolicies {

	public static SnapshotPolicy never()	{
		return version -> false;
	}

	public static SnapshotPolicy always()	{
		return version -> true;
	}

	public static SnapshotPolicy every(final int interval)	{
		checkArgument(interval >= 1);
		return version -> version.value != 0 && version.value % interval == 0;
	}
}
