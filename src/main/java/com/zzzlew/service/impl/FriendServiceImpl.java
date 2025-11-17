package com.zzzlew.service.impl;

import com.zzzlew.mapper.ApplyMapper;
import com.zzzlew.mapper.FriendMapper;
import com.zzzlew.pojo.Apply;
import com.zzzlew.pojo.Friend;
import com.zzzlew.pojo.User;
import com.zzzlew.result.ResponseUtils;
import com.zzzlew.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 20:31
 * @Description: com.zzzlew.service.impl
 * @version: 1.0
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;
    @Autowired
    private ApplyMapper applyMapper;

    @Override
    public Object list(User user) {
        Long userId = user.getId();
        List<User> list = friendMapper.getFriendByUserId(userId);
        return ResponseUtils.ok(list);
    }

    @Override
    public Object add(Apply apply) {
        applyMapper.add(apply);
        return ResponseUtils.ok();
    }

    @Override
    public Object applyList(User user) {
        Long userId = user.getId();
        List<User> list = applyMapper.queryList(userId);
        return ResponseUtils.ok(list);
    }

    @Override
    public Object agree(Friend friend) {
        friendMapper.add(friend);
        Friend friend1 = new Friend();
        friend1.setUid(friend.getFid());
        friend1.setFid(friend.getUid());
        friendMapper.add(friend1);
        return ResponseUtils.ok();
    }

}
