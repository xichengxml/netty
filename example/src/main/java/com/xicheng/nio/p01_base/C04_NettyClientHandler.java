package com.xicheng.nio.p01_base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * description netty 客户端消息处理器
 *
 * @author xichengxml
 * @date 2021/2/10 下午 03:57
 */
public class C04_NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 通道建立连接的时候调用该方法
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello server", CharsetUtil.UTF_8);
        channelHandlerContext.writeAndFlush(byteBuf);
    }

    /**
     * 收到服务端的消息时触发
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("收到服务端的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        channelHandlerContext.close();
    }
}
