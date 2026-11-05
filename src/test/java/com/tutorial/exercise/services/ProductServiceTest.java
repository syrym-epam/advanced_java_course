package com.tutorial.exercise.services;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.tutorial.exercise.components.ProductMetrics;
import com.tutorial.exercise.entities.Product;
import com.tutorial.exercise.repositories.ProductRepository;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMetrics productMetrics;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setName("Phone");
        product.setPrice(new BigDecimal("800.0"));

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product created = productService.createProduct(product);

        assertThat(created.getName()).isEqualTo("Phone");
        assertThat(created.getPrice()).isEqualByComparingTo(new BigDecimal("800.0"));
        verify(productMetrics, times(1)).incrementCreated();
    }
}