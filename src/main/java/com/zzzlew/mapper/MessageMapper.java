package com.zzzlew.mapper;

import com.zzzlew.pojo.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/2 - 11 - 02 - 10:50
 * @Description: com.zzzlew.mapper
 * @version: 1.0
 */
public interface MessageMapper {

    List<Map> list(@Param("from") Long id, @Param("to") Long to);

    void send(Message message);

}
