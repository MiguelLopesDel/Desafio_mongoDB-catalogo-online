package com.miguel.catalogchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class CatalogChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogChallengeApplication.class, args);
    }

}
