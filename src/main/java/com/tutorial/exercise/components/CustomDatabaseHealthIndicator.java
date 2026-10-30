package com.tutorial.exercise.components;


import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator; 
import org.springframework.stereotype.Component;

@Component
public class CustomDatabaseHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        
        boolean healthy = true; 
        if (healthy) {
            return Health.up().withDetail("database", "Available and responsive").build();
        }
        return Health.down().withDetail("database", "Not responding").build();
    }
}