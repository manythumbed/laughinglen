package laughinglen.repository;

import dagger.Module;
import dagger.Provides;

import javax.sql.DataSource;

import static com.google.common.base.Preconditions.checkNotNull;

@Module
public final class DataSourceModule {
	private final DataSource dataSource;

	public DataSourceModule(final DataSource dataSource) {
		this.dataSource = checkNotNull(dataSource);
	}

	@Provides
	public DataSource providesDataSource() {
		return dataSource;
	}
}
