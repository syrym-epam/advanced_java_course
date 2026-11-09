package com.tutorial.exercise.config;

import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsConfig {

    @Bean
    public UserDetailsService userDetailsService(SecurityUsersProperties properties, PasswordEncoder passwordEncoder) {
        var userDetailsList = properties.getUsers().stream().map(u -> {
            // If password is already encoded (starts with {bcrypt}), use it directly. Otherwise, encode it.
            String encodedPassword = u.getPassword().startsWith("{") 
                ? u.getPassword() 
                : passwordEncoder.encode(u.getPassword());

            return User.builder()
                    .username(u.getUsername())
                    .password(encodedPassword)
                    .roles(u.getRoles().toArray(new String[0]))
                    .build();
        }).collect(Collectors.toList());

        return new InMemoryUserDetailsManager(userDetailsList);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}