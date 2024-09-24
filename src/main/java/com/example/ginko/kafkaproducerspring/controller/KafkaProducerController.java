package com.example.ginko.kafkaproducerspring.controller;

import com.example.ginko.kafkaproducerspring.dto.RequestDto;
import com.example.ginko.kafkaproducerspring.entity.ProducerEntity;
import com.example.ginko.kafkaproducerspring.service.KafkaProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
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

    @PostMapping
    public void sendOrder(@RequestBody RequestDto requestDto) throws JsonProcessingException {
        kafkaProducerService.sendMessage(requestDto);
    }

    @GetMapping
    public List<ProducerEntity> getOrders() {
        return kafkaProducerService.getOrder();
    }
}
