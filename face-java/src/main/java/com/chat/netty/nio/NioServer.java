package com.chat.netty.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    private static final int PORT = 6789;

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();

        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(PORT));
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("NioServer 已启动，端口:" + PORT);
        while (true) {
            int num = selector.select();
            System.out.println("num=" + num);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectionKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();

                if (key.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

                    SocketChannel sc = serverSocketChannel.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                    System.out.println("接收连接来自" + sc);

                    byte[] content = ("Welcome to " + InetAddress.getLocalHost().getHostName() + "!").getBytes();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    byteBuffer.put(content);

                    //翻转缓存区
                    byteBuffer.flip();
                    sc.write(byteBuffer);
                } else if (key.isReadable()) {
                    SocketChannel sc = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    while (true) {
                        buffer.clear();
                        int read = 0;
                        try {
                            read = sc.read(buffer);
                        } catch (IOException e) {
                            sc.close();
                            System.out.println("读取发生异常");
                            break;
                        }
                        if (read == 0) {
                            break;
                        }
                        if (read == -1) {
                            key.cancel();
                            sc.close();
                            break;
                        }
                        String body = new String(buffer.array(), 0, read);
                        System.out.println("客户端发送的数据" + body);
                        buffer.clear();
                        buffer.put(("Did you say '" + body + "'?").getBytes());
                        buffer.flip();
                        sc.write(buffer);
                    }
                } else if (key.isWritable()) {
                    System.out.println("write...");
                }
            }
        }
    }
}
