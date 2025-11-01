package com.zzzlew.config;

import com.zzzlew.exception.TokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.zzzlew.annotaion.LoginUser;
import com.zzzlew.pojo.User;
import com.zzzlew.utils.JwtUtil;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 19:53
 * @Description: com.zzzlew.config
 * @version: 1.0
 */
/**
 * 自定义方法参数解析器：用于解析控制器方法中带有@LoginUser注解的User类型参数 作用：从请求头的JWT令牌中提取登录用户信息，并自动注入到控制器方法的参数中
 */
@Configuration // 标记为配置类，让Spring能够扫描并管理该Bean
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 判断当前解析器是否支持处理目标方法的参数 Spring MVC在解析方法参数前，会调用该方法判断是否由当前解析器处理
     *
     * @param parameter 方法参数对象（包含参数类型、注解等信息）
     * @return true：当前解析器支持处理该参数；false：不支持 这里的判断逻辑：参数类型必须是User（或User的子类），且参数上必须有@LoginUser注解
     */

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 两个条件同时满足，才返回true，表示当前解析器要处理该参数
        return
        // 1. 判断参数类型是否为User（或其子类，isAssignableFrom支持子类判断）
        parameter.getParameterType().isAssignableFrom(User.class) &&
        // 2. 判断参数上是否有@LoginUser注解（通过parameter.hasParameterAnnotation检查）
            parameter.hasParameterAnnotation(LoginUser.class);
    }

    /**
     * 当supportsParameter返回true时，Spring MVC会调用该方法执行具体的参数解析逻辑 最终返回的对象会被注入到控制器方法的参数中
     *
     * @param parameter 方法参数对象
     * @param mavContainer 模型和视图容器（用于存储模型数据，此处未使用）
     * @param webRequest 原生Web请求对象（封装了HttpServletRequest等信息）
     * @param binderFactory 数据绑定工厂（用于参数绑定，此处未使用）
     * @return 解析后的User对象（会被注入到控制器方法参数中）
     * @throws Exception 解析过程中可能抛出的异常（如token为空、无效等）
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 1. 从NativeWebRequest中获取原生的HttpServletRequest对象，用于获取请求头信息
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        // 2. 从请求头中获取"Authorization"字段的值（通常存放JWT令牌）
        String token = httpServletRequest.getHeader("Authorization");

        // 3. 判断token是否为空：如果为空，说明用户未登录，抛出自定义的TokenException异常
        if (StringUtils.isEmpty(token)) {
            throw new TokenException();
        }

        // TODO token过期处理

        // 4. 调用JwtUtil工具类解析token，获取封装了用户信息的User对象并返回
        // 该User对象会被自动注入到控制器方法中带有@LoginUser注解的User参数上
        return jwtUtil.getToken(token);
    }
}
