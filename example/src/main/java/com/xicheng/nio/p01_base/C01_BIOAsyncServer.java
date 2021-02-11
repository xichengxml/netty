package com.xicheng.nio.p01_base;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * description 阻塞IO服务端 异步处理数据
 * 是不是也可以异步处理连接
 *
 * @author xichengxml
 * @date 2021/2/9 下午 09:18
 */
public class C01_BIOAsyncServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9000);
        System.out.println("等待连接...");
        // 阻塞方法
        Socket socket = serverSocket.accept();
        System.out.println("有客户端接入...");
        // 读取数据
        new Thread(() -> {
            try {
                new C01_BIOServerHandler().handle(socket);
            } catch (Exception e) {
                System.out.println("error");
            }
        }).start();
    }
}
