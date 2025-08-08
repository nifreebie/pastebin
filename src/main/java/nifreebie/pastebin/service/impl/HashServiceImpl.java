package nifreebie.pastebin.service.impl;

import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import nifreebie.pastebin.repository.PastesRepository;
import nifreebie.pastebin.service.HashService;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@RequiredArgsConstructor
public class HashServiceImpl implements HashService {
    private final BlockingQueue<String> hashQueue = new LinkedBlockingQueue<>();
    private final PastesRepository pastesRepository;

    @Override
    public String getUniqueHash() {
        if (hashQueue.isEmpty()) {
            generate();
        }
        return hashQueue.poll();
    }

    @Override
    public void generate() {
        String hash;
        do {
            int seed = new Random().nextInt();
            hash = Hashing.sha512()
                    .newHasher()
                    .putLong(seed)
                    .hash()
                    .toString()
                    .substring(0, 6);
        } while (isExistByHash(hash));
        hashQueue.add(hash);
    }

    @Override
    public boolean isExistByHash(String hash) {
        return pastesRepository.existsByDisplayUrl(hash);
    }

    @Override
    public boolean hasPrepared(int minCount) {
        return hashQueue.size() >= minCount;
    }
}
