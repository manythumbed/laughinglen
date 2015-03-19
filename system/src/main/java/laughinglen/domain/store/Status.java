package laughinglen.domain.store;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Status {
	public final boolean succeeded;
	public final Version version;

	private Status(final boolean succeeded, final Version version) {
		this.succeeded = succeeded;
		this.version = checkNotNull(version);
	}

	public static Status failed(final Version version)	{
		return new Status(false, version);
	}

	public static Status succeeded(final Version version)	{
		return new Status(true, version);
	}
}
