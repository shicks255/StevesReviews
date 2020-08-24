package com.steven.hicks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProviderUtc")
public class Application {

    private static int serverPort = 8484;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        System.out.println("Visit swagger at: http://127.0.0.1:" + serverPort + "/swagger");
    }
}
