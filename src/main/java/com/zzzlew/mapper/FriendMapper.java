package com.zzzlew.mapper;

import com.zzzlew.pojo.Friend;
import com.zzzlew.pojo.User;

import java.util.List;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 20:31
 * @Description: com.zzzlew.mapper
 * @version: 1.0
 */
public interface FriendMapper {

    List<User> getFriendByUserId(Long id);

    void add(Friend friend);

}
