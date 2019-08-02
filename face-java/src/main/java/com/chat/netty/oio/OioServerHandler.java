package com.chat.netty.oio;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 服务端处理器
 */
public class OioServerHandler implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public OioServerHandler(Socket socket) throws IOException {
        this.socket = socket;
        InputStream is = socket.getInputStream();
        reader=new BufferedReader(new InputStreamReader(is));
        OutputStream os = socket.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(os), true);
    }


    @Override
    public void run() {
        try {
            writer.println("Welcome to" + InetAddress.getLocalHost().getHostName() + "!");
            String body = "";
            while ((body = reader.readLine()) != null) {
                System.out.println("客服端发送的数据=" + body);
                writer.println("Did you say '" + body + "'?");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 关闭资源
     */
    private void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(writer!=null){
            writer.close();
        }
        if(socket!=null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
