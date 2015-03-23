package laughinglen.kafka.testing;

import com.google.common.collect.Lists;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.util.List;


public class TestingServer {
	public final List<ByteArray> received = Lists.newArrayList();
	private final int port;

	private final EventLoopGroup bossGroup;
	private final EventLoopGroup workerGroup;

	public TestingServer(final int port) {
		this.port = port;
		this.bossGroup = new NioEventLoopGroup();
		this.workerGroup = new NioEventLoopGroup();
	}

	public void start() {
		final ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(final SocketChannel socketChannel) throws Exception {
				socketChannel.pipeline().addLast(new TestingServerHandler(received));
			}
		});
		bootstrap.option(ChannelOption.SO_BACKLOG, 128);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

		try {
			bootstrap.bind(port).sync();
			System.out.println("Server running");
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		System.out.println("Stopping server");
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}

	private class TestingServerHandler extends ChannelHandlerAdapter {
		private final List<ByteArray> received;

		private TestingServerHandler(final List<ByteArray> received) {
			this.received = received;
		}

		@Override
		public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
			ByteBuf in = (ByteBuf) msg;
			System.out.println("Server read");
//				final ByteArray read = new ByteArray();
//				while (in.isReadable()) {
//					final byte b = in.readByte();
//
//					read.add(b);
//					System.out.print((char) b);
//					System.out.flush();
//				}
//				received.add(read);

			ctx.channel().writeAndFlush(msg);
		}

		@Override
		public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
			cause.printStackTrace();
			ctx.close();
		}
	}
}
