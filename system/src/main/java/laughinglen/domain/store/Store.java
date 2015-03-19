package laughinglen.domain.store;

import laughinglen.domain.Event;
import laughinglen.domain.Id;

import java.util.List;

public interface Store {
	EventStream stream(Id id);
	EventStream stream(Id id, Version version);
	Status store(Id id, Version version, List<Event> events);
}
