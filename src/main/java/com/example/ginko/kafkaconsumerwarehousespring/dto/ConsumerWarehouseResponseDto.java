package com.example.ginko.kafkaconsumerwarehousespring.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class ConsumerWarehouseResponseDto {
    private String orderId;
    private String confirmationBy;
    private String status;
}
