package com.zzzlew.prooerties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: zzzlew
 * @Date: 2025/11/1 - 11 - 01 - 12:58
 * @Description: com.zzzlew.prooerties
 * @version: 1.0
 */
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secretKey;
    private long expiration;
    private String tokenName;
}
