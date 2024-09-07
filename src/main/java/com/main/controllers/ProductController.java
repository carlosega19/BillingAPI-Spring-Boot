package com.main.controllers;

import com.main.entities.Product;
import com.main.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
    @Autowired
    ProductService productService;


    // GETs
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/name={name}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        Optional<Product> product = productService.getProductByName(name);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping(value = "/desc={description}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Product> getProductsByDescription(@PathVariable String description) {
        return productService.findAllByDescription(description);
    }

    // POSTs
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return ResponseEntity.ok(product);
    }

    // PUTs
    @PutMapping(value = "/up", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
        return ResponseEntity.ok(product);
    }

    // DELETEs
    @DeleteMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
