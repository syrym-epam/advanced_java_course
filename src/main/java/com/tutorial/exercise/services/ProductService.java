package com.tutorial.exercise.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tutorial.exercise.components.ProductMetrics;
import com.tutorial.exercise.entities.Product;
import com.tutorial.exercise.repositories.ProductRepository;

@Service
public class ProductService {
    
    private final ProductRepository repository;
    private final ProductMetrics productMetrics;

    public ProductService(ProductRepository repository, ProductMetrics productMetrics) {
        this.repository = repository;
        this.productMetrics = productMetrics;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }

    public Product createProduct(Product product) {
        // Ensure ID is null so Spring Data JPA treats it as a new entity (INSERT instead of MERGE)
        product.setId(null);
        Product saved = repository.save(product);
        productMetrics.incrementCreated();
        return saved;
    }

    public Optional<Product> updateProduct(Long id, Product details) {
        return repository.findById(id).map(product -> {
            product.setName(details.getName());
            product.setPrice(details.getPrice());
            return repository.save(product);
        });
    }

    public boolean deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        productMetrics.incrementDeleted();
        return true;
    }
}