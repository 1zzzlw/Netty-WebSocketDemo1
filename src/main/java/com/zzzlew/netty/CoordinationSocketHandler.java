package com.zzzlew.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@ChannelHandler.Sharable
@Slf4j
public class CoordinationSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public Map<String, Channel> cmap = new ConcurrentHashMap<>();

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
        // 接收的消息
        System.out.println(String.format("收到客户端%s的数据：%s", ctx.channel().id(), msg.text()));

        Map map = JSON.parseObject(msg.text(), Map.class);
        String type = map.get("type").toString();
        switch (type) {
            case "1" -> websocketLogin(ctx, map); // 进行登录的操作
            case "2" -> sendMessage(map); // 发送对话消息
            case "3" -> sendCall(map); // 请求视频通话
            case "4" -> acceptCall(map); // 接受视频通话
            case "5" -> askOff(map); // 摄像头打开，请求off
            case "6" -> getoff(map); // 获取off
            case "7" -> answer(map);
            case "8" -> candidate(map);
        }
    }

    private void websocketLogin(ChannelHandlerContext ctx, Map map) {
        String uid = map.get("uid").toString();
        cmap.put("user" + uid, ctx.channel());
        System.out.println("用户" + uid + "登录");
    }

    private void sendMessage(Map map) {
        String to = map.get("to").toString();
        // 判断集合中是否有该用户的channel
        if (cmap.containsKey("user" + to)) {
            // 拿到消息接收者的channel
            Channel channel = cmap.get("user" + to);
            // 封装消息，发送给前端的websocket
            Map<String, Object> obj = new HashMap<>();
            obj.put("type", 2);
            obj.put("uuid", UUID.randomUUID().toString());
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        } else {
            log.info("对方没有在线");
        }
    }

    private void sendCall(Map map) {
        String to = map.get("to").toString();
        log.info("给：{} 打视频通话", to);
        // 判断集合中是否有该用户的channel
        if (cmap.containsKey("user" + to)) {
            // 拿到消息接收者的channel
            Channel channel = cmap.get("user" + to);
            // 封装消息，发送给前端的websocket
            Map<String, Object> obj = new HashMap<>();
            obj.put("type", 3);
            obj.put("uuid", UUID.randomUUID().toString());
            obj.put("from", map.get("uid").toString());
            obj.put("to", to);
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        } else {
            log.info("对方没有在线");
        }
    }

    private void acceptCall(Map map) {
        String to = map.get("to").toString();
        log.info("接受：{} 的视频通话", to);
        // 判断集合中是否有该用户的channel
        if (cmap.containsKey("user" + to)) {
            // 拿到消息接收者的channel
            Channel channel = cmap.get("user" + to);
            // 封装消息，发送给前端的websocket
            Map<String, Object> obj = new HashMap<>();
            obj.put("type", 4);
            obj.put("uuid", UUID.randomUUID().toString());
            obj.put("from", map.get("uid").toString());
            obj.put("to", to);
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        } else {
            log.info("对方没有在线");
        }
    }

    private void askOff(Map map) {
        log.info("接受5号信息");
        String to = map.get("to").toString();
        // 判断集合中是否有该用户的channel
        if (cmap.containsKey("user" + to)) {
            // 拿到消息接收者的channel
            Channel channel = cmap.get("user" + to);
            // 封装消息，发送给前端的websocket
            Map<String, Object> obj = new HashMap<>();
            obj.put("type", 5); // 回应通话
            obj.put("uuid", UUID.randomUUID().toString());
            obj.put("from", map.get("uid").toString());
            obj.put("to", to);
            String sendMsg = JSON.toJSONString(obj);
            log.info("转发type=5给前端，to=user{}，消息内容：{}", to, sendMsg);
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        } else {
            log.info("对方没有在线");
        }
    }

    private void getoff(Map map) {
        log.info("接受6号信息");
        String to = map.get("to").toString();
        if (cmap.containsKey("user" + to)) {
            Channel channel = cmap.get("user" + to);
            Map<String, Object> obj = new HashMap<>();
            obj.put("type", 6); // 回应通话
            obj.put("uuid", UUID.randomUUID().toString());
            obj.put("from", map.get("uid").toString());
            obj.put("message", map.get("message"));
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        } else {
            log.info("对方没有在线");
        }
    }

    private void answer(Map map) {
        log.info("接受7号信息");
        String to = map.get("to").toString();
        if (cmap.containsKey("user" + to)) {
            Channel channel = cmap.get("user" + to);
            Map<String, Object> obj = new HashMap<>();
            obj.put("type", 7); // 回应通话
            obj.put("uuid", UUID.randomUUID().toString());
            obj.put("from", map.get("uid").toString());
            obj.put("message", map.get("message"));
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        } else {
            log.info("对方没有在线");
        }
    }

    private void candidate(Map map) {
        log.info("接受8号信息");
        String to = map.get("to").toString();
        if (cmap.containsKey("user"+to)){
            Channel channel = cmap.get("user" + to);
            Map<String,Object> obj = new HashMap<>();
            obj.put("type",8); // 回应通话
            obj.put("uuid", UUID.randomUUID().toString());
            obj.put("from",map.get("uid").toString());
            obj.put("message",map.get("message"));
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(obj)));
        }else {
            log.info("对方没有在线");
        }
    }

}