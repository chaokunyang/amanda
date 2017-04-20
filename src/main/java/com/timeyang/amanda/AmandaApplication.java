package com.timeyang.amanda;

import com.timeyang.amanda.config.DatabaseInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

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
    @Profile("dev")
    public CommandLineRunner commandLineRunner(DatabaseInitializer databaseInitializer) {
        return args -> databaseInitializer.populate();
    }
}
