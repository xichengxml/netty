package com.xicheng.nio.p01_base;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * description 阻塞IO服务端 同步阻塞
 *
 * @author xichengxml
 * @date 2021/2/9 下午 09:18
 */
public class C01_BIOSyncServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9000);
        System.out.println("等待连接...");
        // 阻塞方法
        Socket socket = serverSocket.accept();
        System.out.println("有客户端接入...");
        // 读取数据
        new C01_BIOServerHandler().handle(socket);
    }
}
