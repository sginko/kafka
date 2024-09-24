package com.example.ginko.kafkaproducerspring.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Confirmation {
    private Long orderId;
    private String confirmationBy;
    private String status;
}
