package com.tutorial.exercise.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.tutorial.exercise.avro.ProductEvent;

@Service
public class KafkaProducerService {

    private static final String TOPIC = "message";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductEvent(ProductEvent event) {
        kafkaTemplate.send(TOPIC, event.getName().toString(), event);
        System.out.println("Published Avro message: " + event);
    }
}