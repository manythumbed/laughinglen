package laughinglen.kafka.protocol;

import laughinglen.kafka.testing.TestingClient;
import laughinglen.kafka.testing.TestingServer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class MessagingTest {

	private static final int port = 10001;
	private static TestingServer server = new TestingServer(port);
	private TestingClient client = new TestingClient("localhost", port, "A");

	@BeforeClass
	public static void startServer()	{
		server.start();
	}

	@AfterClass
	public static void stopServer()	{
		server.stop();
	}

	@Before
	public void startClient()	{
		client.start();
	}

	@After
	public void stopClient()	{
		client.stop();
	}

	@Test
	public void shouldSendMessageToServer()	{
		final TestingClient clientB = new TestingClient("localhost", port, "B");

		clientB.start();

		clientB.send(100);
		client.send(1);
		pause();
		client.send(2);
		clientB.send(200);
		pause();
		clientB.send(300);
		client.send(3);
		pause();

		clientB.stop();
	}

	private void pause() {
		try {
			TimeUnit.SECONDS.sleep(1);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
