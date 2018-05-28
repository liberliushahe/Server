package com.vitea.server;

import com.vitea.handler.DiscardServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {
	private int port;
	public DiscardServer(int port){
		this.port=port;
	}
	public void run() throws InterruptedException{
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		try{
		ServerBootstrap sb=new ServerBootstrap();
		System.out.println("server:run()");
		sb.group(bossGroup, workerGroup)
		   .channel(NioServerSocketChannel.class)
		   .childHandler(new ChannelInitializer<SocketChannel>(){

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// TODO Auto-generated method stub
				 ch.pipeline().addLast(new DiscardServerHandler());
			}
			   
		   })
		   .option(ChannelOption.SO_BACKLOG, 128)          
           .childOption(ChannelOption.SO_KEEPALIVE, true); 
		   ChannelFuture f = sb.bind(port).sync();
		   f.channel().closeFuture().sync();
		}finally{
			workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
		}
	}

}
