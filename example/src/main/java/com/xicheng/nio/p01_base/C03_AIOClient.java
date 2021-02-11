package com.xicheng.nio.p01_base;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * description 异步非阻塞客户端
 *
 * @author xichengxml
 * @date 2021/2/10 下午 03:36
 */
public class C03_AIOClient {

    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel asynchronousSocketChannel = AsynchronousSocketChannel.open();
        asynchronousSocketChannel.connect(new InetSocketAddress("127.0.0.1", 10086));
        // 发送消息
        asynchronousSocketChannel.write(ByteBuffer.wrap("Hello server".getBytes()));
        // 接收服务端发送的消息
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        Integer dataLen = asynchronousSocketChannel.read(byteBuffer).get();
        if (-1 != dataLen) {
            System.out.println("接收到服务端消息：" + new String(byteBuffer.array(), 0, dataLen));
        }
    }
}
