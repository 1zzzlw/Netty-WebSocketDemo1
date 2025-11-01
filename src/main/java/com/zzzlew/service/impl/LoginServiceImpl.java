package com.zzzlew.service.impl;

import com.zzzlew.mapper.UserMapper;
import com.zzzlew.pojo.User;
import com.zzzlew.result.ResponseUtils;
import com.zzzlew.service.LoginService;
import com.zzzlew.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 14:00
 * @Description: com.zzzlew.service.impl
 * @version: 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Object login(Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");

        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            return ResponseUtils.username();
        }

        if (!user.getPassword().equals(password)) {
            return ResponseUtils.username();
        }
        return ResponseUtils.ok(jwtUtil.createJWT(user));
    }
}
