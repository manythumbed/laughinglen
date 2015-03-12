package examples.peekaboo;

import dagger.Component;

@Component(modules = ServiceModule.class)
public interface TestComponent {
	Checker checker();
}
