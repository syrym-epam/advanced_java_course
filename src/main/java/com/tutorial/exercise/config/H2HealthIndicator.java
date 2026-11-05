package com.tutorial.exercise.config;

 
import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"stage", "dev", "local"})
public class H2HealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public H2HealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) {
                return Health.up()
                        .withDetail("database", "H2")
                        .withDetail("status", "Available and responsive")
                        .build();
            }
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("database", "H2")
                    .withDetail("error", e.getMessage())
                    .build();
        }
        return Health.down()
                .withDetail("database", "H2")
                .withDetail("status", "Not responding")
                .build();
    }
}