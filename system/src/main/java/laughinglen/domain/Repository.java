package laughinglen.domain;

import java.util.List;

public interface Repository<I extends Id, E extends Event> {
	List<E> events(I id);
}
