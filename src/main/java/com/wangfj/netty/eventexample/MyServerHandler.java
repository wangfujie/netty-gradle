package com.wangfj.netty.eventexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * socket自定义处理器
 *
 * @author wangfj
 * @datetime 2020-01-02 22:18
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 事件触发器
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //空闲处理事件
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()){
                case READER_IDLE:
                    eventType = "读空闲";break;
                case WRITER_IDLE:
                    eventType = "写空闲";break;
                case ALL_IDLE:
                    eventType = "读写空闲";break;
                default:
            }
            System.out.println(ctx.channel().remoteAddress() + "超时事件：" + eventType);
            ctx.channel().close();
        }
    }
}
