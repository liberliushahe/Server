package com.vitea.server;

import com.vitea.server.handler.ViteaServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class ViteaServer {
	private int port;
	public ViteaServer(int port){
		this.port=port;
	}
	public void run() throws InterruptedException{
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		try{
		ServerBootstrap sb=new ServerBootstrap();
		System.out.println("server:run............");
		sb.group(bossGroup, workerGroup)
		   .channel(NioServerSocketChannel.class)
		   .childHandler(new ChannelInitializer<SocketChannel>(){

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				//分隔符解决拆包和沾包问题
				ByteBuf delimiter=Unpooled.copiedBuffer("$_".getBytes());
				ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(new ViteaServerHandler());
			}
			   
		   })
		   .option(ChannelOption.SO_BACKLOG, 128)          
           .childOption(ChannelOption.SO_KEEPALIVE, true); 
		   //绑定端口 同步等待成功
		   ChannelFuture f = sb.bind(port).sync();
		   //等待服务端监听端口关闭
		   f.channel().closeFuture().sync();
		}finally{
			//退出 释放线程池资源
			workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
		}
	}

}
