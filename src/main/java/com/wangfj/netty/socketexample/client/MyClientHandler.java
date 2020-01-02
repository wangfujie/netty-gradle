package com.wangfj.netty.socketexample.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * 客户端入站处理器
 *
 * @author wangfj
 * @datetime 2020-01-02 22:30
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("server remote address：" + ctx.channel().remoteAddress());
        System.out.println("server msg：" + msg);
        //返回消息
        ctx.channel().writeAndFlush("from client: " + UUID.randomUUID());
    }

    /**
     * 当服务连接建立时执行的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("来自客户端问候");
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
