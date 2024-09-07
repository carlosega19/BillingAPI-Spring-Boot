package com.main.services;

import com.main.entities.Product;
import com.main.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // TEST FUNCTION !!!
    public Optional<Product> getProductByName(String name) {
        return Optional.ofNullable(productRepository.findByName(name));
    }
    public List<Product> findAllByDescription(String description) {
        return productRepository.findAllByDescriptionContaining(description);
    }




}
