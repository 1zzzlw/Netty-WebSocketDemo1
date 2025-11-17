package com.zzzlew.service;

import com.zzzlew.pojo.Apply;
import com.zzzlew.pojo.Friend;
import com.zzzlew.pojo.User;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 20:31
 * @Description: com.zzzlew.service
 * @version: 1.0
 */
public interface FriendService {

    Object list(User user);

    Object add(Apply apply);

    Object applyList(User user);

    Object agree(Friend friend);

}
