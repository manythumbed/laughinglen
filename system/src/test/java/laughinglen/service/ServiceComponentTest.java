package laughinglen.service;

import laughinglen.repository.DataSourceModule;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ServiceComponentTest {

	@Test
	public void shouldCreateService()	{
//		final DataSourceModule dataSourceModule = new DataSourceModule(JdbcConnectionPool.create("jdbc:h2:mem:test", "user", "password"));
//		final ServiceComponent serviceComponent = Dagger_ServiceComponent.builder().dataSourceModule(dataSourceModule).controlModule(new ControlModule(Controls.ACTIVE)).build();
//
//		assertNotNull(serviceComponent.service());
	}

}