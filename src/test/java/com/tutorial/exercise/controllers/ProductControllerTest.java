package com.tutorial.exercise.controllers;


import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.exercise.entities.Product;
import com.tutorial.exercise.services.ProductService;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean 
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String jwtToken;


    @Test
    @WithMockUser(roles = "USER")
    void testGetAllProducts() throws Exception {
        Product p = new Product();
        p.setName("Tablet");
        p.setPrice(300.0);

        when(productService.getAllProducts()).thenReturn(List.of(p));

        mockMvc.perform(get("/api/products")                
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tablet"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateProduct() throws Exception {
        Product p = new Product();
        p.setName("Monitor");
        p.setPrice(250.0);

        when(productService.createProduct(any(Product.class))).thenReturn(p);

        mockMvc.perform(post("/api/products")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Monitor"));
    }
}