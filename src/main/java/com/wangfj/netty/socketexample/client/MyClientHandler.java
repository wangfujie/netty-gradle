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
public class MyClientHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server remote address: " + ctx.channel().remoteAddress());
        if (msg instanceof byte[]){
            System.out.println("server byte[] msg: " + new String((byte[]) msg, "UTF-8"));
        }else {
            System.out.println("server other msg: " + msg);
            //((PooledUnsafeDirectByteBuf)msg).getByte(0)
        }
    }

    /**
     * 当服务连接建立时执行的方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("来自客户端问候~~");
        System.out.println("连接建立时，发送消息到服务端-----------------");
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
