package laughinglen.domain.store;

@FunctionalInterface
public interface SnapshotPolicy	{
	boolean snapshot(Version version);
}
