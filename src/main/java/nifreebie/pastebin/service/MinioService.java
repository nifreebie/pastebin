package nifreebie.pastebin.service;

public interface MinioService {
    void uploadFile(String objectKey, String text);

    String downloadFile(String objectKey);

    void deleteFile(String objectKey);
}