package com.zzzlew.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zzzlew.annotaion.LoginUser;
import com.zzzlew.pojo.Apply;
import com.zzzlew.pojo.Friend;
import com.zzzlew.pojo.User;
import com.zzzlew.service.FriendService;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 20:29
 * @Description: com.zzzlew.controller
 * @version: 1.0
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping
    public Object list(@LoginUser User user) {
        return friendService.list(user);
    }

    // 添加好友
    @PostMapping("/add")
    public Object add(@LoginUser User user, @RequestBody Apply apply) {
        apply.setUid(user.getId());
        return friendService.add(apply);
    }

    @GetMapping("/ApplyList")
    public Object applyList(@LoginUser User user) {
        return friendService.applyList(user);
    }

    @PostMapping("/agree")
    public Object agree(@LoginUser User user, @RequestBody Friend friend) {
        friend.setUid(user.getId());
        return friendService.agree(friend);
    }


}
