package com.wangfj.netty.websocketexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * WebSocket的channel初始化器
 *
 * @author wangfj
 * @datetime 2020-01-06 22:08
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        //将分段聚合成完整的FullHttpRequest或者FullHttpResponse
        pipeline.addLast((new HttpObjectAggregator(1024*9)));
        //webSocket服务处理类（握手，close，ping，pong）,设置ws的url
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        //自定义handle,webSocket文本祯handle
        pipeline.addLast(new TextWebSocketFrameHandler());
    }
}
