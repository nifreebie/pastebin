package nifreebie.pastebin.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient(MinioCredentialsConfig minioCredentialsConfig) {
        return MinioClient.builder()
                .endpoint(minioCredentialsConfig.endpoint())
                .credentials(minioCredentialsConfig.accessKey(), minioCredentialsConfig.secretKey())
                .build();
    }
}
