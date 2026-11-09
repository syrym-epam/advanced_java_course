package com.resource.demo.services;

import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// import com.resource.demo.avro.ProductEvent;

@Service
public class ServiceResource {

    @KafkaListener(topics = "message", groupId = "microservice")
    public void consumeMessage(GenericRecord event) {
    System.out.println("Consumed Avro message successfully!");
    System.out.println("Product Name: " + event.get("name"));
    System.out.println("Product Price: " + event.get("price"));
    }
}