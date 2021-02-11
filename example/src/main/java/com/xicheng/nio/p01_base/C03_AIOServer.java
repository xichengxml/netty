package com.xicheng.nio.p01_base;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * description 异步非阻塞服务端
 *
 * @author xichengxml
 * @date 2021/2/10 下午 03:15
 */
public class C03_AIOServer {

    public static void main(String[] args) throws Exception {
        AsynchronousServerSocketChannel asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(10086));
        asynchronousServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel asynchronousSocketChannel, Object attachment) {
                System.out.println("2-" + Thread.currentThread().getName());
                // 接收客户端的链接
                asynchronousServerSocketChannel.accept(attachment, this);
                // 读取客户端发送的信息
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                asynchronousSocketChannel.read(byteBuffer, attachment, new CompletionHandler<Integer, Object>() {
                    @Override
                    public void completed(Integer result, Object attachment) {
                        System.out.println("3-" + Thread.currentThread().getName());
                        byteBuffer.flip();
                        System.out.println("接收到消息：" + new String(byteBuffer.array(), 0, result));
                        asynchronousSocketChannel.write(ByteBuffer.wrap("Hello client".getBytes()));
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        exc.printStackTrace();
                    }
                });

            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
        System.out.println("1-" + Thread.currentThread().getName());
        Thread.sleep(Integer.MAX_VALUE);
    }
}
