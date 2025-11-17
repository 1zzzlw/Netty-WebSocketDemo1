package com.zzzlew.mapper;

import com.zzzlew.pojo.Apply;
import com.zzzlew.pojo.User;

import java.util.List;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 23:49
 * @Description: com.zzzlew.mapper
 * @version: 1.0
 */
public interface ApplyMapper {

    void add(Apply apply);

    List<User> queryList(Long id);

}
