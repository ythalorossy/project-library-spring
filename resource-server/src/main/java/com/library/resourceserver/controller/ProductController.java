package com.library.resourceserver.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.resourceserver.model.Product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ProductController {

    private static List<Product> productList = new LinkedList<>(Set.of(
            Product.builder().id("001").name("gpt-1").build(),
            Product.builder().id("002").name("gpt-2").build()));

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productList);
    }

    @PostMapping("/product")
    public ResponseEntity<Void> postMethodName(@RequestBody Product product) {
        productList.add(product);
        return ResponseEntity.ok().build();
    }
}
