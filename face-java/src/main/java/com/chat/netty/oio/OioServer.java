package com.chat.netty.oio;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * OIO 主要的问题在于每当有一个新的客户端请求接入时，服务端都要创建一个新的线程处理新接入的客户端连接，
 * 一个线程只能处理一个客户端连接。
 * 在高性能服务器应用领域，面向成千上万个客户端的并发连接，这种模型显然无法满足高性能、高并发接入的场景。
 *
 *
 */
public class OioServer {

    public static void main(String[] args) throws IOException {
        OioServer server = new OioServer();
        server.server(6789);
    }

    public void server(final int port) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Oio启动，端口" + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("接收来自" + socket);
                new Thread(new OioServerHandler(socket)).start();
            }
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
                System.out.println("OioServer已关闭");
            }
        }
    }
}
