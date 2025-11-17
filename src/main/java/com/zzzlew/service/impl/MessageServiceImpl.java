package com.zzzlew.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzzlew.mapper.MessageMapper;
import com.zzzlew.pojo.Message;
import com.zzzlew.pojo.PageQuery;
import com.zzzlew.pojo.User;
import com.zzzlew.result.ResponseUtils;
import com.zzzlew.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/2 - 11 - 02 - 10:48
 * @Description: com.zzzlew.service.impl
 * @version: 1.0
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Object list(User user, PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<Map> list = messageMapper.list(user.getId(), pageQuery.getTo());
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("total", PageInfo.of(list).getTotal());
        return ResponseUtils.ok(map);
    }

    @Override
    public Object send(Message message) {
        message.setType(1);
        message.setTime(new Date());
        messageMapper.send(message);
        return ResponseUtils.ok();
    }
}
