package examples.peekaboo;

import dagger.Component;
import examples.peekaboo.implementation.ServiceModule;

@Component(modules = ServiceModule.class)
public interface TestComponent {
	Checker checker();
}
