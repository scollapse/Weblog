package per.stu.weblog.admin.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description: Minio配置类
 * @author: syl
 * @create: 2025-01-02 17:52
 **/
@ConfigurationProperties(prefix = "minio")
@Data
@Component
public class MinioProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
