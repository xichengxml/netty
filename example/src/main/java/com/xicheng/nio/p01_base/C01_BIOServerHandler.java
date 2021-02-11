package com.xicheng.nio.p01_base;

import java.net.Socket;
import java.nio.charset.Charset;

/**
 * description 服务端消息处理器
 *
 * @author xichengxml
 * @date 2021/2/10 上午 11:17
 */
public class C01_BIOServerHandler {

    public void handle(Socket clientSocket) throws Exception {
        System.out.println("准备read...");
        byte[] bytes = new byte[1024];
        // 接收客户端的数据，阻塞方法，客户端没有数据时就等待
        int dataLen = clientSocket.getInputStream().read(bytes);
        if (-1 != dataLen) {
            System.out.println("接收到客户端的数据: " + new String(bytes, 0, dataLen));
        }
        // 向客户端回传报文
        clientSocket.getOutputStream().write("Hello client".getBytes(Charset.defaultCharset()));
        clientSocket.getOutputStream().flush();
    }
}
