package dn.spring.scaffold.file.config;

import dn.spring.scaffold.common.exception.BizException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.http.HttpStatusCode;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.utils.StringUtils;
import software.amazon.awssdk.utils.Validate;

import java.net.URI;

@Configuration
public class OssConfig {

    private final FileStorageProperties fileStorageProperties;

    public OssConfig(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
    }

    @Bean(destroyMethod = "close")
    public S3Client ossClient() {
        validate();

        S3Client s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.builder()
                        .accessKeyId(fileStorageProperties.getAccessKey())
                        .secretAccessKey(fileStorageProperties.getSecretKey())
                        .build()))
                .region(Region.of(fileStorageProperties.getRegion()))
                .endpointOverride(URI.create(fileStorageProperties.getEndpoint()))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(Boolean.TRUE.equals(fileStorageProperties.getPathStyleAccessEnabled()))
                        .chunkedEncodingEnabled(false)
                        .build())
                .build();

        initBucket(s3Client, fileStorageProperties.getBucketName());
        return s3Client;
    }

    private void validate() {
        if (StringUtils.isBlank(fileStorageProperties.getEndpoint())) {
            throw new BizException("project.file.endpoint is required");
        }
        if (StringUtils.isBlank(fileStorageProperties.getRegion())) {
            throw new BizException("project.file.region is required");
        }
        if (StringUtils.isBlank(fileStorageProperties.getAccessKey())) {
            throw new BizException("project.file.access-key is required");
        }
        if (StringUtils.isBlank(fileStorageProperties.getSecretKey())) {
            throw new BizException("project.file.secret-key is required");
        }
        if (StringUtils.isBlank(fileStorageProperties.getBucketName())) {
            throw new BizException("project.file.bucket-name is required");
        }
    }

    private void initBucket(S3Client ossClient, String bucketName) {
        if (doesBucketExist(ossClient, bucketName)) {
            return;
        }
        ossClient.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
    }

    private boolean doesBucketExist(S3Client ossClient, String bucketName) {
        try {
            Validate.notEmpty(bucketName, "The bucket name must not be null or an empty string.", "");
            ossClient.getBucketAcl(request -> request.bucket(bucketName));
            return true;
        } catch (AwsServiceException exception) {
            if (exception.statusCode() == HttpStatusCode.MOVED_PERMANENTLY) {
                return true;
            }
            if (exception.awsErrorDetails() != null
                    && "AccessDenied".equals(exception.awsErrorDetails().errorCode())) {
                return true;
            }
            if (exception.statusCode() == HttpStatusCode.NOT_FOUND) {
                return false;
            }
            throw exception;
        }
    }
}
