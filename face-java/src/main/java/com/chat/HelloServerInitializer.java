package com.chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //通过socketChannel获取对应的管道
        ChannelPipeline pipeline=ch.pipeline();
        //通过管道，添加handler
        //当请求到服务端，我们需要做解码，响应到客户端做编码
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());
        pipeline.addLast("customHandler",new CustomHandler());

    }
}
