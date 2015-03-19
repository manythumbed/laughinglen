package laughinglen.domain.testing;

import laughinglen.domain.Root;

public class TestRoot extends Root {
	private int eventCount = 0;

	@SuppressWarnings("UnusedDeclaration")
	private void handle(final TestEvent ignored)	{
		eventCount++;
	}

	public int count()	{
		return eventCount;
	}

	public void increment()	{
		record(new TestEvent());
	}

	public void bigIncrement()	{
		increment();
		increment();
	}
}
