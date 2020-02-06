package com.wangfj.netty.socketexample.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * 客户端
 *
 * @author wangfj
 * @datetime 2020-01-02 22:24
 */
public class MyClient {

    public static void main(String[] args) throws InterruptedException {
        //事件循环组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
            //bootstrap.group(eventLoopGroup).channel(EpollSocketChannel.class)
            //客户端初始化器
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    //解码 Decoders
                    pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
                    pipeline.addLast("bytesDecoder", new ByteArrayDecoder());

                    //编码 Encoder
                    pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                    pipeline.addLast("bytesEncoder", new ByteArrayEncoder());

                    //添加字符串解码编码处理
                    //pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0,4,0,4));
                    //pipeline.addLast(new LengthFieldPrepender(4));
                    //pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                    //pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                    //自定义处理类
                    pipeline.addLast(new MyClientHandler());
                }
            });

            //连接服务端
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 10302).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
