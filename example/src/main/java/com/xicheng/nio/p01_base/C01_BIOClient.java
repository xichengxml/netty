package com.xicheng.nio.p01_base;

import java.net.Socket;
import java.nio.charset.Charset;

/**
 * description 客户端
 *
 * @author xichengxml
 * @date 2021/2/9 下午 09:18
 */
public class C01_BIOClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 9000);
        System.out.println("向服务端发送数据...");
        // 向服务端发送数据
        socket.getOutputStream().write("Hello server".getBytes(Charset.defaultCharset()));
        socket.getOutputStream().flush();

        // 读取服务端回传的报文
        byte[] bytes = new byte[1024];
        int dataLen = socket.getInputStream().read(bytes);
        if (-1 != dataLen) {
            System.out.println("接收到服务端的数据: " + new String(bytes));
        }
    }
}
