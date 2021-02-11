package com.xicheng.nio.p01_base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * description netty服务端消息处理器
 * 自定义Handler需要继承netty提供的HandlerAdapter（规范）
 * @author xichengxml
 * @date 2021/2/10 下午 03:56
 */
public class C04_NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端的数据
     * @param channelHandlerContext 上下文，含有通道Channel，管道pipeline
     * @param msg 客户端发送的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        System.out.println("服务器读取线程：" + Thread.currentThread().getName());
        Channel channel = channelHandlerContext.channel();
        // 本质上是个双向链表
        ChannelPipeline pipeline = channelHandlerContext.pipeline();
        // 将msg转换成ByteBuf对象，类似于NIO的ByteBuffer
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("接收到客户端消息：" + byteBuf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 数据读取完毕处理方法
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello client", CharsetUtil.UTF_8);
        channelHandlerContext.writeAndFlush(byteBuf);
    }

    /**
     * 处理异常，一般是要关闭通道
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        channelHandlerContext.close();
    }
}
