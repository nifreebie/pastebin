package nifreebie.pastebin.service.scheduler;

import lombok.RequiredArgsConstructor;
import nifreebie.pastebin.service.HashService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HashScheduler {
  private final HashService hashService;

  @Value("${app.min-hash-count}")
  private int minHashCount;

  @Scheduled(fixedDelay = 1000)
  public void prepareHashes() {
    while (!hashService.hasPrepared(minHashCount)) {
      hashService.generate();
    }
  }
}