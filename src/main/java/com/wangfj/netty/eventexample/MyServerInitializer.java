package com.wangfj.netty.eventexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.TimeUnit;

/**
 * socket初始化器
 *
 * @author wangfj
 * @datetime 2020-01-02 22:13
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //空闲检测处理器
        pipeline.addLast(new IdleStateHandler(5,7,10, TimeUnit.SECONDS));
        //自定义处理类
        pipeline.addLast(new MyServerHandler());
    }
}
