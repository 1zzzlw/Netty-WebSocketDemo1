package com.zzzlew.pojo;

import lombok.Data;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/2 - 11 - 02 - 10:12
 * @Description: com.zzzlew.pojo
 * @version: 1.0
 */
@Data
public class Friend {

    private Long id;
    private Long uid;
    private Long fid;

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", uid=" + uid +
                ", fid=" + fid +
                '}';
    }
}
