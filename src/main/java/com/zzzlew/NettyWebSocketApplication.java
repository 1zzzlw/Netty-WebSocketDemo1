package com.zzzlew;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zzzlew.netty.CoordinationNettyServer;

import jakarta.annotation.Resource;

@SpringBootApplication
@MapperScan("com.zzzlew.mapper")
public class NettyWebSocketApplication implements CommandLineRunner {

    @Resource
    private CoordinationNettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(NettyWebSocketApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        nettyServer.start();
    }
}
