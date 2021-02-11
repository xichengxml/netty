package com.xicheng.nio.p02_chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.SocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * description
 *
 * @author xichengxml
 * @date 2021/2/10 下午 05:40
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    // GloabalEventExecutor.INSTANCE是全局事件管理器，单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        Channel channel = channelHandlerContext.channel();
        SocketAddress socketAddress = channel.remoteAddress();

        // 将该客户上线的消息推送其他在线客户端
        // 该方法会遍历所有的客户端，发送消息
        String msg = dateFormat.format(new Date()) + ", 客户端" + socketAddress + "上线了\n";
        channelGroup.writeAndFlush(msg);
        // 将该客户端加入ChannelGroup
        channelGroup.add(channel);
        System.out.println(msg);
    }

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        // 获取当前channel
        Channel channel = channelHandlerContext.channel();
        // 遍历，根据不同的情况，发送不同的消息
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(dateFormat.format(new Date()) + "，客户端" + channel.remoteAddress() + "发送消息：" + msg + "\n");
            } else {
                ch.writeAndFlush(dateFormat.format(new Date()) + "自己发送消息: " + msg);
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        Channel channel = channelHandlerContext.channel();
        SocketAddress socketAddress = channel.remoteAddress();

        // 将该客户上线的消息推送其他在线客户端
        // 该方法会遍历所有的客户端，发送消息
        String msg = dateFormat.format(new Date()) + ", 客户端" + socketAddress + "下线了\n";
        channelGroup.writeAndFlush(msg);
        System.out.println(msg);
        // 不需要从ChannelGroup手动移除该客户端
        System.out.println(channelGroup.size());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        channelHandlerContext.close();
    }
}
