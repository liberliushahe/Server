package com.vitea.client.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ViteaClientHandler extends ChannelInboundHandlerAdapter{
	private int count;
	static final String ECHO="hello my name is lifei .$_";
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		long start=System.currentTimeMillis();
		for(int i=0;i<200000;i++) {
			ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO.getBytes()));
		}
		System.out.println(System.currentTimeMillis()-start);
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		super.channelRead(ctx, msg);
		System.out.println("this id "+ ++count +"time receive server:["+msg+"]");
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
	
	
	
	
	

	

}
