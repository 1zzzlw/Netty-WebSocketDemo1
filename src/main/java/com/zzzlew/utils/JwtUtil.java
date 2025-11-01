package com.zzzlew.utils;

import com.alibaba.fastjson.JSON;
import com.zzzlew.pojo.User;
import com.zzzlew.prooerties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Resource;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: zzzlew
 * @Date: 2025/10/31 - 10 - 31 - 23:49
 * @Description: com.zzzlew.utils
 * @version: 1.0
 */

public class JwtUtil {

    @Resource
    private JwtProperties jwtProperties;

    // 生成令牌
    public String createJWT(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        return generatorToken(jwtProperties.getExpiration(), map);
    }

    public String generatorToken(long ttlMillis, Map<String, Object> claims) {
        // 指定签名的时候使用的签名算法，也就是header那部分
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm,
            jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)).setExpiration(exp);

        return builder.compact();
    }

    // 解析令牌
    public User getToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8))
            .parseClaimsJws(token).getBody();
        LinkedHashMap userMap = (LinkedHashMap)claims.get("user");
        return JSON.parseObject(JSON.toJSONString(userMap), User.class);
    }
}
