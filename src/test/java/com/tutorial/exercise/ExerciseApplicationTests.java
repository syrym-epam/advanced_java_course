package com.tutorial.exercise;


import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.exercise.components.JwtTokenProvider;
import com.tutorial.exercise.entities.Product;
import com.tutorial.exercise.repositories.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ExerciseApplicationTests {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String jwtToken;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        jwtToken = jwtTokenProvider.generateToken("adminuser", List.of("ROLE_ADMIN", "ROLE_USER"));
    }

    @Test
    void testCreateAndGetProductEndToEnd() throws Exception {
        Product product = new Product();
        product.setName("Smartwatch");
        product.setPrice(199.99);

        mockMvc.perform(post("/api/products")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Smartwatch"));

        mockMvc.perform(get("/api/products")
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Smartwatch"));
    }
}