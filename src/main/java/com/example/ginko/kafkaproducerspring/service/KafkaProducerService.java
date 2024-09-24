package com.example.ginko.kafkaproducerspring.service;

import com.example.ginko.kafkaproducerspring.dto.Confirmation;
import com.example.ginko.kafkaproducerspring.dto.RequestDto;
import com.example.ginko.kafkaproducerspring.dto.SendOrderDto;
import com.example.ginko.kafkaproducerspring.entity.ProducerEntity;
import com.example.ginko.kafkaproducerspring.mapper.ProducerMapper;
import com.example.ginko.kafkaproducerspring.repository.ProducerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ProducerRepository producerRepository;
    private final ProducerMapper producerMapper;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "orders-confirmation", groupId = "confirmation-group")
    public void listenConfirmation(String confirmationJson) throws JsonProcessingException {
        Confirmation confirmation = objectMapper.readValue(confirmationJson, Confirmation.class);
        String confirmationBy = confirmation.getConfirmationBy();
        System.out.println("Producer received confirmation: " + confirmationBy);

        ProducerEntity producerEntity = producerRepository.findById(confirmation.getOrderId()).orElse(null);

        if (producerEntity != null) {
            if ("Warehouse".equals(confirmation.getConfirmationBy())) {
                producerEntity.setWarehouseConfirmation("Confirmed by Warehouse");
            } else if ("Finance".equals(confirmation.getConfirmationBy())) {
                producerEntity.setFinanceConfirmation("Confirmed by Finance");
            }
            producerRepository.save(producerEntity);
//            System.out.println("Order updated in database with confirmation: " + confirmation);
//        } else {
//            System.out.println("Order with ID " + confirmation.getOrderId() + " not found.");
//        }
        }
    }

    @Transactional
    public void sendMessage(RequestDto requestDto) throws JsonProcessingException {
        ProducerEntity producerEntity = new ProducerEntity(requestDto.getProdOrder(), null, null);
        producerRepository.save(producerEntity);

        SendOrderDto sendOrderDto = producerMapper.toSendOrderDto(producerEntity);
        String sendOrderJson = objectMapper.writeValueAsString(sendOrderDto);
        kafkaTemplate.send("orders", sendOrderJson);
        System.out.println("Producer sent order: " + sendOrderJson);
    }

    public List<ProducerEntity> getOrder() {
        return producerRepository.findAll();
    }
}
