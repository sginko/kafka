package com.example.ginko.kafkaconsumerfinanceclassic;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerFinanceClassic {
    public static void main(String[] args) {
        // Create Kafka consumer configuration
        Properties consumerProps = new Properties();
        consumerProps.put("bootstrap.servers", "localhost:9092");  // Kafka broker address
        consumerProps.put("group.id", "finance-group");  // Consumer group ID
        consumerProps.put("enable.auto.commit", "true");  // Automatically commit offsets
        consumerProps.put("auto.commit.interval.ms", "1000");
        consumerProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // Set auto_offset_reset to 'latest' so that we consume only new messages
        consumerProps.put("auto.offset.reset", "latest");

        // Create the Kafka consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consumerProps);

        // Create Kafka producer configuration
        Properties producerProps = new Properties();
        producerProps.put("bootstrap.servers", "localhost:9092");
        producerProps.put("acks", "all");
        producerProps.put("retries", 0);
        producerProps.put("batch.size", 16384);
        producerProps.put("linger.ms", 1);
        producerProps.put("buffer.memory", 33554432);
        producerProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Create the Kafka producer
        Producer<String, String> producer = new KafkaProducer<>(producerProps);

        // Subscribe to the topic
        consumer.subscribe(Collections.singletonList("orders"));

        // Continuously poll the topic for new messages
        try {
            while (true) {
                // Poll messages with a timeout of 1 second
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Finance consumed message: key = %s, value = %s, partition = %d, offset = %d%n",
                            record.key(), record.value(), record.partition(), record.offset());

                    // Send confirmation message to 'orders-confirmation' topic
                    String confirmationMessage = "Finance confirmed order: " + record.value();
                    producer.send(new ProducerRecord<>("orders-confirmation", confirmationMessage));
                    System.out.println("Finance sent confirmation: " + confirmationMessage);
                }
            }
        } finally {
            consumer.close();  // Ensure consumer is closed on exit
            producer.close();  // Ensure producer is closed on exit
        }
    }
}
