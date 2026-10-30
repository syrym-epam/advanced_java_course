package com.tutorial.exercise.controllers;
 

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import com.tutorial.exercise.components.JwtTokenProvider;

@RestController
public class AuthController {

    private final JwtTokenProvider tokenProvider;

    AuthController(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
 
        
        if ("admin".equals(username) && "password".equals(password)) {
            String token = tokenProvider.generateToken(username, List.of("ROLE_ADMIN", "ROLE_USER"));
            return ResponseEntity.ok(Map.of("token", token));
        }
        if ("user".equals(username) && "password".equals(password)) {
            String token = tokenProvider.generateToken(username, List.of("ROLE_USER"));
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }
}