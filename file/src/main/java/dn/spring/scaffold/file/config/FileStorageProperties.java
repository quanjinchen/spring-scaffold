package dn.spring.scaffold.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "project.file")
public class FileStorageProperties {

    private String endpoint;

    private String region;

    private String accessKey;

    private String secretKey;

    private String bucketName;

    private Boolean pathStyleAccessEnabled = Boolean.TRUE;

    private String publicUrlPrefix = "";
}
