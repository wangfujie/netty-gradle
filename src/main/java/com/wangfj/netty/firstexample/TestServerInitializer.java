package com.wangfj.netty.firstexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 自定义初始化器
 *
 * @author wangfj
 * @datetime 2020-01-02 21:06
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //增加到最后
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        //将自定义的通道处理器加入管道
        pipeline.addLast("testHttpServerHandler", new TestHttpServerHandler());
    }
}
