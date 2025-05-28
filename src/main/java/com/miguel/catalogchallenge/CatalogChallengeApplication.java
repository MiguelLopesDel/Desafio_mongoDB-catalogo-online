package com.miguel.catalogchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableMongoRepositories
@EnableAsync
public class CatalogChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogChallengeApplication.class, args);
    }

}
