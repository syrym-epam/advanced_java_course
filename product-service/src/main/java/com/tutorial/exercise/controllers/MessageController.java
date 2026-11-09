
package com.tutorial.exercise.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.exercise.dto.ProductRequestDto;
import com.tutorial.exercise.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final ProductService productService;

    MessageController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/product")
    public ResponseEntity<ProductRequestDto> create(@Valid @RequestBody ProductRequestDto requestDto) {
        
        productService.sendProduct(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(requestDto);
    }

    // @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    // public ResponseEntity<?> create(@RequestBody String text) {
        
    //     productService.sendText(text);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(text);
    // }
}