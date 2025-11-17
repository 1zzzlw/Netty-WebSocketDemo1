package com.zzzlew.service.impl;

import com.zzzlew.mapper.UserMapper;
import com.zzzlew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 22:50
 * @Description: com.zzzlew.service.impl
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Object search(String username) {
        return userMapper.getUserByUsername(username);
    }
}
