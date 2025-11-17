package com.zzzlew.controller;

import com.zzzlew.annotaion.LoginUser;
import com.zzzlew.pojo.Message;
import com.zzzlew.pojo.PageQuery;
import com.zzzlew.pojo.User;
import com.zzzlew.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/2 - 11 - 02 - 10:37
 * @Description: com.zzzlew.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/list")
    public Object list(@LoginUser User user, PageQuery pageQuery) {
        return messageService.list(user, pageQuery);
    }

    @PostMapping("/send")
    public Object send(@LoginUser User user, @RequestBody Message message) {
        message.setFrom(user.getId());
        return messageService.send(message);
    }

}
