package nifreebie.pastebin.service.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import nifreebie.pastebin.config.MinioCredentialsConfig;
import nifreebie.pastebin.service.MinioService;
import nifreebie.pastebin.util.BadRequestException;
import nifreebie.pastebin.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {
    private final MinioCredentialsConfig config;
    private final MinioClient minioClient;

    @Override
    public void uploadFile(String objectKey, String text){
        try (ByteArrayInputStream bais = new ByteArrayInputStream(
                text.getBytes(StandardCharsets.UTF_8))
        ) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(config.bucket())
                            .object(objectKey)
                            .stream(bais, bais.available(), -1)
                            .contentType("text/plain")
                            .build()
            );
        } catch (Exception e) {
            throw new BadRequestException("Ошибка при загрузке контента в S3 хранилище");
        }
    }

    @Override
    public String downloadFile(String objectKey) {
        try (var stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(config.bucket())
                        .object(objectKey)
                        .build()
        )) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new NotFoundException("Ошибка при чтении контента в S3 хранилище");
        }
    }

    @Override
    public void deleteFile(String objectKey) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(config.bucket())
                            .object(objectKey)
                            .build()
            );
        } catch (Exception e) {
            throw new BadRequestException("Ошибка при удалении файла из S3 хранилища");
        }
    }
}
