package com.example.ginko.kafkaconsumerfinancespring.service;

import com.example.ginko.kafkaconsumerfinancespring.dto.ConsumerFinanceRequestDto;
import com.example.ginko.kafkaconsumerfinancespring.dto.ConsumerFinanceResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class KafkaConsumerFinanceService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "orders", groupId = "finance-group")
    public void consume(String orderJson) throws JsonProcessingException {
        ConsumerFinanceRequestDto consumerFinanceRequestDto = objectMapper.readValue(orderJson, ConsumerFinanceRequestDto.class);
        String orderId = consumerFinanceRequestDto.getOrderId();
        String prodOrder = consumerFinanceRequestDto.getProdOrder();
        System.out.println("Finance consumed message: " + prodOrder);

        ConsumerFinanceResponseDto confirmation = new ConsumerFinanceResponseDto(orderId, "Finance", "Processed");
        String confirmationJson = objectMapper.writeValueAsString(confirmation);
        kafkaTemplate.send("orders-confirmation", confirmationJson);
        System.out.println("Finance sent confirmation: " + confirmationJson);
    }
}
