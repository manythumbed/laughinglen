package laughinglen.service;

import javax.inject.Inject;
import javax.sql.DataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class Service {
	private final Control control;
	private final DataSource dataSource;

	@Inject
	public Service(final Control control, final DataSource dataSource) {
		this.control = checkNotNull(control);
		this.dataSource = checkNotNull(dataSource);
	}

	public void start()	{
	}

	public void stop()	{
	}
}
