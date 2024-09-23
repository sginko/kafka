package com.example.ginko.kafkaconsumerfinancespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaConsumerFinanceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(KafkaConsumerFinanceApplication.class);
        app.setAdditionalProfiles("kafkaconsumerfinance");
        app.run(args);
    }
}
