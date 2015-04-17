package laughinglen.domain;

@FunctionalInterface
public interface Notifier {
	void notify(Id id, Event event);
}
