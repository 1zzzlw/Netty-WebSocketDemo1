package com.zzzlew.controller;

import com.zzzlew.result.ResponseUtils;
import com.zzzlew.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 13:55
 * @Description: com.zzzlew.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Object login(@RequestBody Map<String, String> map) {
        if (CollectionUtils.isEmpty(map)) {
            return ResponseUtils.badArgument();
        }
        return loginService.login(map);
    }
}
