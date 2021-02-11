package com.xicheng.nio.p01_base;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * description 使用selector
 *
 * @author xichengxml
 * @date 2021/2/10 下午 12:59
 */
public class C02_NIOSelectorServer {

    public static void main(String[] args) throws Exception {
        // 创建NIO ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 打开Selector处理Channel,即创建epoll
        Selector selector = Selector.open();
        // 把ServerSocketChannel注册到Selector上
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务启动成功...");

        while (true) {
            // 阻塞等待需要处理的事件发生
            selector.select();
            // 获取Selector中注册的SelectorKey实例
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            // 遍历处理SelectorKey中的事件
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 如果是连接事件，进行连接获取和事件注册
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel selectorServer = (ServerSocketChannel) selectionKey.channel();
                    // 获取客户端Channel
                    SocketChannel socketChannel = selectorServer.accept();
                    socketChannel.configureBlocking(false);
                    // 注册客户端Channel到Selector
                    // 这里只注册读事件，如果需要也可以注册写事件
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端连接成功");
                }
                // 如果是读取事件，进行读取和打印
                if (selectionKey.isReadable()) {
                    SocketChannel selectorClient = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int dataLen = selectorClient.read(byteBuffer);
                    if (dataLen > 0) {
                        System.out.println("接收到数据: " + new String(byteBuffer.array()));
                    }
                    if (dataLen == -1) {
                        System.out.println("客户端断开连接");
                        selectorClient.close();
                    }
                }
                // 从事件集合里删除本次处理的事件key，防止Selector重复处理
                iterator.remove();
            }

        }
    }
}
