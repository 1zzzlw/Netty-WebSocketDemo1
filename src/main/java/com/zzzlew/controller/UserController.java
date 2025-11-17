package com.zzzlew.controller;

import com.zzzlew.result.ResponseUtils;
import com.zzzlew.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzzlew.annotaion.LoginUser;
import com.zzzlew.pojo.User;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 20:02
 * @Description: com.zzzlew.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Object User(@LoginUser User user) {
        return ResponseUtils.ok(user);
    }

    @GetMapping("/search")
    public Object search(String username) {
        return userService.search(username);
    }

}
