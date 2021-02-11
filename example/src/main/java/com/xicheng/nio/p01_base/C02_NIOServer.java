package com.xicheng.nio.p01_base;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * description 普通NIO 服务端
 *
 * @author xichengxml
 * @date 2021/2/10 下午 12:38
 */
public class C02_NIOServer {

    private static List<SocketChannel> channelList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        // 创建NIO ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        System.out.println("服务启动成功...");

        while (true) {
            // 非阻塞模式的accept不会阻塞，是操作系统内部实现的，底层调用的linux的accept函数
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (null != socketChannel) {
                System.out.println("连接成功...");
                // 设置socketChannel为非阻塞
                socketChannel.configureBlocking(false);
                // 保存客户端到list中
                channelList.add(socketChannel);
            }

            // 遍历读取数据
            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel clientSocketChannel = iterator.next();
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                int dataLen = clientSocketChannel.read(byteBuffer);
                if (dataLen > 0) {
                    System.out.println("接收到数据: " + new String(byteBuffer.array()));
                } else if (-1 == dataLen){
                    iterator.remove();
                    System.out.println("断开连接...");
                }
            }
        }
    }
}
