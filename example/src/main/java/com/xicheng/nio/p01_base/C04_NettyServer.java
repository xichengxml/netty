package com.xicheng.nio.p01_base;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * description netty 服务端
 *
 * @author xichengxml
 * @date 2021/2/10 下午 03:56
 */
public class C04_NettyServer {

    public static void main(String[] args) {
        // 创建两个线程组，一主多从模式
        // boss线程组只处理客户端连接请求，其他的业务处理交给worker线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务端启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 使用链式编程来配置参数
            bootstrap.group(bossGroup, workerGroup)
                    // 使用NioServerSocketChannel作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 对workerGroup的SocketChannel设置处理器
                            socketChannel.pipeline().addLast(new C04_NettyServerHandler());
                        }
                    });
            System.out.println("服务器启动成功...");
            // 绑定端口且同步，返回一个异步对象，通过isDone方法可以判断异步事件的执行情况
            // 启动服务器，并绑定端口，bind是异步操作，sync是等待异步方法执行完毕
            ChannelFuture channelFuture = bootstrap.bind(9000).sync();
            // 给ChannelFuture注册监听器，监听我们关心的事件
            channelFuture.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    System.out.println("监听9000端口成功");
                } else {
                    System.out.println("监听9000端口失败");
                }
            });
            // 对通道关闭进行监听，closeFuture是异步操作，监听通道关闭
            // 通过sync方法同步等待通道关闭完毕，这里会阻塞等待通道关闭完成
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
