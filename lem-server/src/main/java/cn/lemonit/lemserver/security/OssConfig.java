package cn.lemonit.lemserver.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oss")
@Data
public class OssConfig {
    private String serverPath;
    private String accessKey;
    private String secretKey;
}
