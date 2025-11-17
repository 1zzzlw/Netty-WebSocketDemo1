package com.zzzlew.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/2 - 11 - 02 - 10:51
 * @Description: com.zzzlew.pojo
 * @version: 1.0
 */
@Data
public class Message {

    private Long id;
    private Long from;
    private Long to;
    private Integer type;
    private String content;
    private Date time;

}
