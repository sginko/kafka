package com.example.ginko.kafkaproducerspring.controller;

import com.example.ginko.kafkaproducerspring.service.KafkaProducerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/send-order")
public class KafkaProducerController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProducerService kafkaProducerService;
    private final List<String> orders = new ArrayList<>();

    @KafkaListener(topics = "orders-confirmation", groupId = "confirmation-group")
    public void listenConfirmation(String confirmation) {
        System.out.println("Producer received confirmation: " + confirmation);
    }

    @PostMapping
    public void sendOrder(@RequestBody String order) {
        kafkaProducerService.sendMessage("orders", order);
//        System.out.println("Producer sent order: " + order);
        orders.add(order);
    }

    @GetMapping
    public ResponseEntity<List<String>> getOrders() {
        return ResponseEntity.ok(orders);
    }
}
