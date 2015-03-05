package laughinglen.service;

import dagger.Module;
import dagger.Provides;

import static com.google.common.base.Preconditions.checkNotNull;

@Module
public class ControlModule {
	private final Control control;

	public ControlModule(final Control control) {
		this.control = checkNotNull(control);
	}

	@Provides
	public Control providesControl()	{
		return control;
	}
}
