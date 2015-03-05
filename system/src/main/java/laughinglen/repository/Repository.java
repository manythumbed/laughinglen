package laughinglen.repository;

import dagger.Component;

import javax.sql.DataSource;

@Component(modules = DataSourceModule.class)
public interface Repository {
	DataSource dataSource();
}
