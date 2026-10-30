package com.tutorial.exercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.exercise.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
