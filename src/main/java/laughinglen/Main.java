package laughinglen;

import laughinglen.repository.DataSourceModule;
import laughinglen.service.ControlModule;
import laughinglen.service.Controls;
import laughinglen.service.Dagger_ServiceComponent;
import laughinglen.service.ServiceComponent;

public class Main {

	public static void main(final String[] args)	{
		final DataSourceModule dataSourceModule = new DataSourceModule(null);
		final ControlModule controlModule = new ControlModule(Controls.ACTIVE);
		final ServiceComponent serviceComponent = Dagger_ServiceComponent.builder().dataSourceModule(dataSourceModule).controlModule(controlModule).build();

		serviceComponent.service().start();
	}
}
