package laughinglen.service;

import dagger.Component;
import laughinglen.repository.DataSourceModule;

@Component(modules = {DataSourceModule.class, ControlModule.class})
public interface ServiceComponent {
	Service service();
}
