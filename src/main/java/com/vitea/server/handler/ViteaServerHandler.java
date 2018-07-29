package com.vitea.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ViteaServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        int count=0;
		String body=(String)msg;
		System.out.println("this is "+ ++count +"times receive cient:["+body+"]");
        body+="$_";
        ByteBuf echo=Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(echo);
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 发送异常关闭链路
		super.exceptionCaught(ctx, cause);
		cause.printStackTrace();
		ctx.close();
	}

}
