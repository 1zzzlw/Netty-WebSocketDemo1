package com.zzzlew.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 19:49
 * @Description: com.zzzlew.annotaion
 * @version: 1.0
 */
// 只在运行的时候生效
@Retention(RetentionPolicy.RUNTIME)
// 只在参数方法上生效
@Target(ElementType.PARAMETER)
public @interface LoginUser {
}
