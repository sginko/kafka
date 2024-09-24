package com.example.ginko.kafkaproducerspring.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "producer_orders")
@Setter
@Getter
public class ProducerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prodOrder;

    private String warehouseConfirmation;

    private String financeConfirmation;

//    @Version
//    private Integer version;

    public ProducerEntity(String prodOrder, String warehouseConfirmation, String financeConfirmation) {
        this.prodOrder = prodOrder;
        this.warehouseConfirmation = warehouseConfirmation;
        this.financeConfirmation = financeConfirmation;
//        this.version = 0;
    }
}
