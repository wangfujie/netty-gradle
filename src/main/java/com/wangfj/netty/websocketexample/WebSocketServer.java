package com.wangfj.netty.websocketexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.net.InetSocketAddress;

/**
 * webSocket服务
 *
 * @author wangfj
 * @datetime 2020-01-06 22:06
 */
public class WebSocketServer {

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
                    //定义初始化器
                    .childHandler(new WebSocketChannelInitializer());
            //定义webSocket服务端端口
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8899)).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
