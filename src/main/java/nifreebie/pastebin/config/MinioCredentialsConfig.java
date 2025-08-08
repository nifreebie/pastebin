package nifreebie.pastebin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio", ignoreUnknownFields = false)
public record MinioCredentialsConfig(
        String endpoint,
        String accessKey,
        String secretKey,
        String bucket
) {}
