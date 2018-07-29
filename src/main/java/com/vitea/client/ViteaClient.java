package com.vitea.client;

import com.vitea.client.handler.ViteaClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * client
 * @author liushahe
 *
 */
public class ViteaClient {
	public void connect(int port,String host)throws Exception{
		//配置客户端线程组
		EventLoopGroup group=new NioEventLoopGroup();
		try {
		Bootstrap b=new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
		.option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ByteBuf delimiter=Unpooled.copiedBuffer("$_".getBytes());
				ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new ViteaClientHandler());
			}
			
		});
		//发起异步连接请求
		ChannelFuture f=b.connect(host, port).sync();
		//等待客户端链路关闭
		f.channel().closeFuture().sync();
		}finally {
			group.shutdownGracefully();
		}
	}
	public static void main(String[] args) {
		int port=12145;
		try {
			new ViteaClient().connect(port, "127.0.0.1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
