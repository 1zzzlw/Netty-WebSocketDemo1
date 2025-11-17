package com.zzzlew.service;

import com.zzzlew.pojo.Message;
import com.zzzlew.pojo.PageQuery;
import com.zzzlew.pojo.User;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/2 - 11 - 02 - 10:47
 * @Description: com.zzzlew.service
 * @version: 1.0
 */
public interface MessageService {

    Object list(User user, PageQuery pageQuery);

    Object send(Message message);

}
