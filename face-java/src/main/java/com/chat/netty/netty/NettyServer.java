package com.chat.netty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServer {
    private int port;
    public NettyServer(int port){
        this.port=port;
    }

    private void start() throws InterruptedException {
        // 创建两个线程组，其中bossGroup 用于监听端口，接受新的连接。
        //  workerGroup 用于处理传入的客户端连接的数据读写。
        EventLoopGroup bossGroup =new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            ServerBootstrap b=new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline= ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new LineBasedFrameDecoder(1024));
                            pipeline.addLast(new NettySeverHandler());
                        }
                    });
            ChannelFuture future=b.bind(port).sync();
            System.out.println("NettyServer已启动,监听端口:"+port);
            future.channel().closeFuture().sync();
        }catch (Exception e){


        }finally {
            bossGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NettyServer server=new NettyServer(6789);
        server.start();
    }
}
