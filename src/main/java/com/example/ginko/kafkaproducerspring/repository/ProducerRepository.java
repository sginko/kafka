package com.example.ginko.kafkaproducerspring.repository;

import com.example.ginko.kafkaproducerspring.entity.ProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepository extends JpaRepository<ProducerEntity, Long> {
}
