package com.wangfj.netty.socketexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * socket自定义处理器
 *
 * @author wangfj
 * @datetime 2020-01-02 22:18
 */
public class MyServerHandler extends SimpleChannelInboundHandler<Object> {

    public ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // to keep track of open sockets
        group.add(ctx.channel());
        System.out.println("有客户端连接上了：" + ctx.channel());
        //发送消息
        group.writeAndFlush(("欢迎：" + ctx.channel() + "--->" + LocalDateTime.now()).getBytes());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client remote address：" + ctx.channel().remoteAddress());
        System.out.println("client msg：" + msg);
        //返回消息
        group.writeAndFlush(("from server: " + UUID.randomUUID()).getBytes());
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
