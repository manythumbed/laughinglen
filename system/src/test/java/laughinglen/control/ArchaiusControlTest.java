package laughinglen.control;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.config.ConcurrentMapConfiguration;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicPropertyFactory;
import org.junit.Test;
import rx.Subscription;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

public class ArchaiusControlTest {

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotCreateWithNullProperty() {
		new ArchaiusControl(null, null);
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotCreateWithNullFactory() {
		new ArchaiusControl("test", null);
	}

	@Test
	public void shouldCreateSuccessfully() {
		assertThat(new ArchaiusControl("test", DynamicPropertyFactory.getInstance())).isNotNull();
	}

	@Test
	public void shouldObserveState() throws InterruptedException {
		final ConcurrentMapConfiguration configuration = new ConcurrentMapConfiguration(Maps.newHashMap());
		configuration.setProperty("archaius.fixedDelayPollingScheduler.initialDelayMills", 10);
		configuration.setProperty("archaius.fixedDelayPollingScheduler.delayMills", 100);
		configuration.setProperty("test", true);

		ConfigurationManager.install(configuration);

		final DynamicPropertyFactory factory = DynamicPropertyFactory.getInstance();
		final ArchaiusControl control = new ArchaiusControl("test", factory);

		final ArrayList<State> states = Lists.newArrayList();
		final Subscription subscription = control.changes().subscribe(states::add);

		configuration.setProperty("test", false);
		TimeUnit.MILLISECONDS.sleep(5);

		configuration.setProperty("test", true);
		TimeUnit.MILLISECONDS.sleep(5);

		configuration.setProperty("test", false);
		TimeUnit.MILLISECONDS.sleep(5);

		configuration.setProperty("test", true);
		TimeUnit.MILLISECONDS.sleep(5);

		assertThat(control.current().active).isTrue();
		assertThat(states.size()).isEqualTo(4);
		assertThat(states.get(0).active).isFalse();
		assertThat(states.get(1).active).isTrue();
		assertThat(states.get(2).active).isFalse();
		assertThat(states.get(3).active).isTrue();

		subscription.unsubscribe();

	}
}