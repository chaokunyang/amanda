package com.timeyang.amanda;

import com.timeyang.amanda.core.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author chaokunyang
 * @create 2017-04-15
 */
@SpringBootApplication
public class AmandaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmandaApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }
}
