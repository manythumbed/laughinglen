package laughinglen.domain.store;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public final class Version {
	public final long value;

	public Version(final long value) {
		checkArgument(value >= 0);
		this.value = value;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final Version version = (Version) o;

		return value == version.value;
	}

	public boolean check(final long required)	{
		return value == required;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}


	public static Version ZERO = new Version(0);

}
