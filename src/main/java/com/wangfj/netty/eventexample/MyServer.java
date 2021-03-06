package com.wangfj.netty.eventexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * socket服务
 *
 * @author wangfj
 * @datetime 2020-01-02 22:11
 */
public class MyServer {

    public static void main(String[] args) throws InterruptedException {
        //定义线程组，boss接收分发给worker
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //针对于bossGroup的
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //定义初始化器，针对于workerGroup的
                    .childHandler(new MyServerInitializer());
            //定义服务端端口
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
