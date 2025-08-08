package nifreebie.pastebin;

import nifreebie.pastebin.config.MinioCredentialsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({MinioCredentialsConfig.class})
@EnableScheduling
@EnableCaching
public class PastebinApplication {

    public static void main(String[] args) {
        SpringApplication.run(PastebinApplication.class, args);
    }

}
