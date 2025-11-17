package com.zzzlew.pojo;

import lombok.Data;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/2 - 11 - 02 - 13:05
 * @Description: com.zzzlew.pojo
 * @version: 1.0
 */
@Data
public class PageQuery {

    private Long to;
    private Integer pageNum;
    private Integer pageSize;

}
