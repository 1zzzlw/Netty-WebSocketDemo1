package com.zzzlew.mapper;

import com.zzzlew.pojo.User;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 14:04
 * @Description: com.zzzlew.mapper
 * @version: 1.0
 */
public interface UserMapper {

    User getUserByUsername(String username);

}
