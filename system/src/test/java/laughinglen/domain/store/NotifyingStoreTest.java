package laughinglen.domain.store;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import laughinglen.domain.Event;
import laughinglen.domain.Id;
import laughinglen.domain.store.memory.MemoryStore;
import laughinglen.domain.testing.TestEvent;
import laughinglen.domain.testing.TestId;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class NotifyingStoreTest {

	@Test
	public void shouldNotifyOnEveryEventIfStoreSucceeds()	{
		final Map<Id, Event> notifications = Maps.newHashMap();
		final NotifyingStore store = new NotifyingStore(new MemoryStore(), notifications::put);

		final TestId id1 = new TestId("1");
		final TestEvent event = new TestEvent();
		final Status status = store.store(id1, Version.ZERO, Lists.newArrayList(event));
		assertThat(status.succeeded).isTrue();
		assertThat(status.version).isEqualTo(new Version(1));
		assertThat(notifications).containsEntry(id1, event);
	}
}