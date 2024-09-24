package com.example.ginko.kafkaproducerspring.mapper;

import com.example.ginko.kafkaproducerspring.dto.SendOrderDto;
import com.example.ginko.kafkaproducerspring.entity.ProducerEntity;

public class ProducerMapper {
    public SendOrderDto toSendOrderDto(ProducerEntity entity) {
        return new SendOrderDto(entity.getId().toString(), entity.getProdOrder());
    }
}
