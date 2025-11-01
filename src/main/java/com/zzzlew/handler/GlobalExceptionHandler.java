package com.zzzlew.handler;

/**
 * @Auther: zzzlew
 * @Date: 2025/10/31 - 10 - 31 - 23:45
 * @Description: com.zzzlew.handler
 * @version: 1.0
 */

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zzzlew.exception.TokenException;
import com.zzzlew.result.ResponseUtils;

import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("异常信息：{}", e.getMessage());
        // 创建全局的Token异常类
        if (e instanceof TokenException) {
            return ResponseUtils.unlogin();
        } else if (e instanceof MalformedJwtException) {
            // 表示 JWT 令牌格式不正确（不符合 JWT 规范）的错误
            return ResponseUtils.unlogin();
        } else if (e instanceof ExpiredJwtException) {
            // 用于表示 JWT 令牌已过期 的错误
            return ResponseUtils.unlogin();
        } else {
            return ResponseUtils.fail();
        }
    }

}
