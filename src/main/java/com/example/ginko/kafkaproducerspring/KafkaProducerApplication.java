package com.example.ginko.kafkaproducerspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(KafkaProducerApplication.class);
        app.setAdditionalProfiles("kafkaproducer");
        app.run(args);
    }

    //java -jar target/your-app-name.jar --server.port=8081
}
