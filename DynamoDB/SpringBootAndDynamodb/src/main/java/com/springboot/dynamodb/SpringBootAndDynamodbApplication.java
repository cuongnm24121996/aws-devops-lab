package com.springboot.dynamodb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringBootAndDynamodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAndDynamodbApplication.class, args);
        log.info("Springboot and dynamodb application started successfully.");
    }
}
