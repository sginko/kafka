package com.example.ginko.kafkaconsumerwarehousespring.service;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class KafkaConsumerWarehouseService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "orders", groupId = "finance-group")
    public void consume(ConsumerRecord<String, String> record) {
        String consumedMessage = record.value();
        System.out.println("Warehouse consumed message: " + consumedMessage);

        String replyMessage = "Processed: " + consumedMessage;
        kafkaTemplate.send("orders-confirmations", replyMessage);
        System.out.println("Warehouse sent confirmation 'orders': " + replyMessage);
    }

//    @KafkaListener(topics = "orders", groupId = "warehouse-group")
//    public void consume(ConsumerRecord<String, String> record) {
//        System.out.println("Warehouse consumed message: " + record.value());
//
//        String confirmationMessage = "Warehouse confirmation: " + record.value();
//        sendConfirmation(confirmationMessage);
//    }
//
//    private void sendConfirmation(String message) {
//        ProducerRecord<String, String> record = new ProducerRecord<>("orders-confirmation", message);
//        kafkaTemplate.send(record);
//        System.out.println("Warehouse sent confirmation: " + message);
//    }
}
