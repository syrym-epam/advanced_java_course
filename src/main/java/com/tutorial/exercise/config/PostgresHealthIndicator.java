package com.tutorial.exercise.config;


import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class PostgresHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public PostgresHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) { // Timeout in seconds
                return Health.up()
                        .withDetail("database", "PostgreSQL")
                        .withDetail("status", "Available and responsive")
                        .build();
            }
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("database", "PostgreSQL")
                    .withDetail("error", e.getMessage())
                    .build();
        }
        return Health.down()
                .withDetail("database", "PostgreSQL")
                .withDetail("status", "Not responding")
                .build();
    }
}