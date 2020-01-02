package com.wangfj.netty.socketexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * socket自定义处理器
 *
 * @author wangfj
 * @datetime 2020-01-02 22:18
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("client remote address：" + ctx.channel().remoteAddress());
        System.out.println("client msg：" + msg);
        //返回消息
        ctx.channel().writeAndFlush("from server: " + UUID.randomUUID());
    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
