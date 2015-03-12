package examples.peekaboo;

import dagger.Module;
import dagger.Provides;

import javax.inject.Named;

@Module
public class ServiceModule {
	private final String check;

	public ServiceModule(final String check) {
		this.check = check;
	}

	@Provides @Named("reliable")
	public Service providesReliable()	{
		return new ReliableService(check);
	}

	@Provides @Named("unreliable")
	public  Service providesUnreliable()	{
		return new UnreliableService(check);
	}
}
