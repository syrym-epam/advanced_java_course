package com.tutorial.exercise.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.security")
public class SecurityUsersProperties {
    private List<UserConfig> users;

    @Data
    public static class UserConfig {
        private String username;
        private String password;
        private List<String> roles;
    }
}