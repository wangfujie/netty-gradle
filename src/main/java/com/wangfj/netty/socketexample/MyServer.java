package com.wangfj.netty.socketexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

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
                    //定义初始化器
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            //添加解码器
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0,4,0,4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            //pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            //pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));

                            pipeline.addLast(new ByteArrayDecoder());
                            pipeline.addLast(new ByteArrayEncoder());

                            //增加自定义的客户端处理器
                            pipeline.addLast("myHandler", new MyServerHandler());
                        }
                    });
            //定义服务端端口
            ChannelFuture channelFuture = serverBootstrap.bind("127.0.0.1", 8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
