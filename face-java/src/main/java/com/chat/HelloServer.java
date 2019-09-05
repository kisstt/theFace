package com.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 实现客户端发送请求
 */
public class HelloServer {

    public static void main(String[] args) throws InterruptedException {
        //定义一对线程组
        //主线程组,接收客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //从线程组,主线程会把线程任务丢给他，处理IO
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //Netty服务器的启动类
        serverBootstrap.group(bossGroup, workerGroup)      //设置主从线程组
                .channel(NioServerSocketChannel.class)   //设置NIO的双向通道
                .childHandler(new HelloServerInitializer());  //处理workerGroup
        ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
        channelFuture.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
