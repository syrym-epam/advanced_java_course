package com.tutorial.exercise.components;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class ProductMetrics {

    private final Counter productCreatedCounter;
    private final Counter productDeletedCounter;

    public ProductMetrics(MeterRegistry registry) {
        this.productCreatedCounter = Counter.builder("exercise.products.created")
                .description("Total number of products created")
                .register(registry);

        this.productDeletedCounter = Counter.builder("exercise.products.deleted")
                .description("Total number of products deleted")
                .register(registry);
    }

    public void incrementCreated() {
        productCreatedCounter.increment();
    }

    public void incrementDeleted() {
        productDeletedCounter.increment();
    }
}