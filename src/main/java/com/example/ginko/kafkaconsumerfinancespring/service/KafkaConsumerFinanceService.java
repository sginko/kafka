package com.example.ginko.kafkaconsumerfinancespring.service;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class KafkaConsumerFinanceService {
    //    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerFinanceService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;


    @KafkaListener(topics = "orders", groupId = "finance-group")
    public void consume(ConsumerRecord<String, String> record) {
        String consumedMessage = record.value();
        System.out.println("Finance consumed message: " + consumedMessage);

        String replyMessage = "Processed: " + consumedMessage;
        kafkaTemplate.send("orders-confirmations", replyMessage);
        System.out.println("Finance sent confirmation 'my-reply-topic': " + replyMessage);
    }

//    @KafkaListener(topics = "orders", groupId = "finance-group")
//    public void consume(ConsumerRecord<String, String> record) {
//        System.out.println("Finance consumed message: " + record.value());
//        logger.info("Consumed message: {}", record.value());
//
//        String confirmationMessage = "Finance confirmation: " + record.value();
//        sendConfirmation(confirmationMessage);
//
//    }
//
//    private void sendConfirmation(String message) {
//        ProducerRecord<String, String> record = new ProducerRecord<>("orders-confirmation", message);
//        kafkaTemplate.send(record);
//        kafkaTemplate.send("orders-confirmations", message);
//        System.out.println("Finance sent confirmation: " + message);
//    }
}
