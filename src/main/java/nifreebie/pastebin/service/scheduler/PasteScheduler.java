package nifreebie.pastebin.service.scheduler;

import lombok.RequiredArgsConstructor;
import nifreebie.pastebin.repository.PastesRepository;
import nifreebie.pastebin.service.MinioService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasteScheduler {
    private final PastesRepository pastesRepository;
    private final MinioService minioService;

    @Scheduled(fixedRate = 1000)
    @Transactional
    public void clearExpiredPastes() {
        pastesRepository.findMinioKeysOfExpiredPastes().forEach(minioKey -> {
            minioService.deleteFile(minioKey);
            evictCache(minioKey.replace("pastes/", "").replace(".txt", ""));
        });
        pastesRepository.deleteExpiredPastes();
    }

    @CacheEvict(value = "pastes", key = "#displayUrl")
    public void evictCache(String displayUrl) {
    }
}
