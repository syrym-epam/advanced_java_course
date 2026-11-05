package com.tutorial.exercise.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.exercise.config.SecurityConfig;
import com.tutorial.exercise.dto.ProductRequestDto;
import com.tutorial.exercise.entities.Product;
import com.tutorial.exercise.services.ProductService;

@WebMvcTest(ProductController.class)
@Import(SecurityConfig.class) // Ensures Spring Security filter chain is active in tests
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean 
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(roles = "USER")
    void testGetAllProductsAsUser() throws Exception {
        Product p = new Product();
        p.setId(1L);
        p.setName("Tablet");
        p.setPrice(new BigDecimal("300.0"));

        when(productService.getAllProducts()).thenReturn(List.of(p));

        mockMvc.perform(get("/api/products")                
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tablet"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateProductAsAdmin() throws Exception {
        ProductRequestDto requestDto = new ProductRequestDto();
        requestDto.setName("Monitor");
        requestDto.setPrice(new BigDecimal("250.0"));

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName("Monitor");
        savedProduct.setPrice(new BigDecimal("250.0"));

        when(productService.createProduct(any(Product.class))).thenReturn(savedProduct);

        mockMvc.perform(post("/api/products")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Monitor"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testDeleteProductAsUserForbidden() throws Exception {
        // A regular USER should not be allowed to delete products (@PreAuthorize("hasRole('ADMIN')"))
        mockMvc.perform(delete("/api/products/1")
                .with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteProductAsAdminSuccess() throws Exception {
        when(productService.deleteProduct(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/products/1")
                .with(csrf()))
                .andExpect(status().isNoContent());
    }
}