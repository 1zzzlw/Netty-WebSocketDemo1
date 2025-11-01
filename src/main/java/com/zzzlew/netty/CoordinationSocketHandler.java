package com.zzzlew.netty;


import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
@ChannelHandler.Sharable
public class CoordinationSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public Map<String, Channel> cmap = new HashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端建立连接，通道开启！");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与客户端断开连接，通道关闭！");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //接收的消息
        Map map = JSON.parseObject(msg.text(), Map.class);
        String type = map.get("type").toString();
        System.out.println(String.format("收到客户端%s的数据：%s", ctx.channel().id(), msg.text()));
    }
}