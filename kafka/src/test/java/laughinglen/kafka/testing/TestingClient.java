package laughinglen.kafka.testing;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TestingClient {
	private final String host;
	private final int port;
	private final String label;

	private final EventLoopGroup workerGroup;

	private Channel channel;

	public TestingClient(final String host, final int port, final String label) {
		this.host = host;
		this.port = port;
		this.label = label;
		this.workerGroup = new NioEventLoopGroup();
	}

	public void start()	{
		final Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(workerGroup);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(final SocketChannel socketChannel) throws Exception {
				socketChannel.pipeline().addLast(new TestingClientHandler(label));
			}
		});

		try {
			final ChannelFuture future = bootstrap.connect(host, port).sync();
			channel = future.channel();
		}
		catch (InterruptedException ignored) {
		}
	}

	public void send(final long number)	{
		System.out.println("Sending data");
		try {
			final ByteBuf buffer = Unpooled.buffer();
			buffer.writeLong(number);
			channel.writeAndFlush(buffer).sync();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Data sent");
	}

	public void stop()	{
		System.out.println("Stopping client");
		workerGroup.shutdownGracefully();
	}

	private class TestingClientHandler extends ChannelHandlerAdapter	{
		private final String label;

		private TestingClientHandler(final String label) {
			this.label = label;
		}

		@Override
		public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
			System.out.println("CLIENT ERROR");
			cause.printStackTrace();
		}

		@Override
		public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
			try {
				final ByteBuf buffer = (ByteBuf) msg;

				System.out.printf("%s RX: %d\n", label, buffer.readLong());
			}
			finally {
				((ByteBuf) msg).release();
			}
		}
	}
}
