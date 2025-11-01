package com.zzzlew.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许所有来源的跨域请求
        corsConfiguration.addAllowedOrigin("*");
        // 允许所有请求头
        corsConfiguration.addAllowedHeader("*");
        // 允许所有 HTTP 方法（GET/POST/PUT/DELETE 等）
        corsConfiguration.addAllowedMethod("*");
        // 允许前端获取响应头中的 Authorization 字段
        corsConfiguration.addExposedHeader("Authorization");
        return corsConfiguration;
    }

    @Bean
    // 注册 CORS 过滤器
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有接口生效
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    @Override
    // 通过 MVC 注册 CORS 规则
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 对所有接口生效
            .allowedOrigins("*") // 允许所有来源
            .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的 HTTP 方法
            .maxAge(3600); // 预检请求的缓存时间（秒），表示 3600 秒内无需重复发送预检请求
    }

}
