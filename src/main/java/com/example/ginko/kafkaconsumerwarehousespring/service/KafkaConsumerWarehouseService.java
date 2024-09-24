package com.example.ginko.kafkaconsumerwarehousespring.service;

import com.example.ginko.kafkaconsumerfinancespring.dto.ConsumerFinanceResponseDto;
import com.example.ginko.kafkaconsumerwarehousespring.dto.ConsumerWarehouseRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class KafkaConsumerWarehouseService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "orders", groupId = "warehouse-group")
    public void consume(String orderJson) throws JsonProcessingException {
        ConsumerWarehouseRequestDto consumerWarehouseRequestDto = objectMapper.readValue(orderJson, ConsumerWarehouseRequestDto.class);
        String orderId = consumerWarehouseRequestDto.getOrderId();
        String prodOrder = consumerWarehouseRequestDto.getProdOrder();
        System.out.println("Warehouse consumed message: " + prodOrder);

        ConsumerFinanceResponseDto confirmation = new ConsumerFinanceResponseDto(orderId, "Warehouse", "Processed");
        String confirmationJson = objectMapper.writeValueAsString(confirmation);
        kafkaTemplate.send("orders-confirmation", confirmationJson);
        System.out.println("Warehouse sent confirmation: " + confirmationJson);
    }
}
