package com.zzzlew.netty;

import org.springframework.context.annotation.Configuration;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import jakarta.annotation.Resource;

@Configuration
public class CoordinationNettyServer {
    @Resource
    private CoordinationSocketHandler coordinationSocketHandler;

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            // TODO 用于设置 TCP 连接的 半连接队列（backlog）大小
            sb.option(ChannelOption.SO_BACKLOG, 1024);
            sb.group(group, bossGroup) // 绑定线程池
                .channel(NioServerSocketChannel.class) // 指定使用的channel
                .localAddress(8004)// 绑定监听端口
                .childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                        ch.pipeline().addLast(new HttpServerCodec());
                        // 以块的方式来写的处理器， 是 Netty 中处理大文件/大数据流传输的核心处理器，作用是支持 “分块写入”，避免一次性发送超大数据导致的内存溢出或阻塞。
                        // 核心作用：分块传输大数据
                        ch.pipeline().addLast(new ChunkedWriteHandler());
                        // 处理 HTTP 消息碎片化 的核心处理器，把这些碎片化的 HTTP 消息 “拼接” 成一个完整的 FullHttpRequest 或 FullHttpResponse
                        ch.pipeline().addLast(new HttpObjectAggregator(8192));
                        // 核心！负责 HTTP 协议升级为 WebSocket 协议，同时 自动处理 WebSocket 帧的拆分与组装（相当于 WebSocket 专属的 “帧解码器”）；
                        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", "WebSocket", true, 65536 * 10));
                        // 直接接收完整的 WebSocket 消息（如文本、二进制数据），不用关心半包。
                        ch.pipeline().addLast(coordinationSocketHandler);// 自定义消息处理类
                    }
                });
            ChannelFuture cf = sb.bind().sync(); // 服务器异步创建绑定
            System.out.println(CoordinationNettyServer.class + "已启动，正在监听： " + cf.channel().localAddress());
            cf.channel().closeFuture().sync(); // 关闭服务器通道
        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
            bossGroup.shutdownGracefully().sync();
        }
    }
}
