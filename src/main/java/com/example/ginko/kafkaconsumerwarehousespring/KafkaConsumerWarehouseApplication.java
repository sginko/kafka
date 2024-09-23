package com.example.ginko.kafkaconsumerwarehousespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaConsumerWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(KafkaConsumerWarehouseApplication.class);
        app.setAdditionalProfiles("kafkaconsumerwarehouse");
        app.run(args);
    }
}
