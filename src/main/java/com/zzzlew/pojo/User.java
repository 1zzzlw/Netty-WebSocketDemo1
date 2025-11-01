package com.zzzlew.pojo;

import lombok.Data;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 0:05
 * @Description: com.zzzlew.pojo
 * @version: 1.0
 */
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String image;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
