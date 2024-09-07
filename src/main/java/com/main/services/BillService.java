package com.main.services;

import com.main.entities.Bill;
import com.main.entities.Product;
import com.main.entities.Sell;
import com.main.entities.User;
import com.main.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    public Bill save(Bill bill, Long id) {
        // Looking for some user
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) return null;

        // List used to save the modified products
        List<Product> products = new ArrayList<>();
        Optional<Product> product;

        // Save the total amount
        double total = 0;

        // Setting and checking the sells
        for (Sell sell : bill.getSells()) {
            sell.setBill(bill);

            product = productService.getProductById(sell.getProduct().getId());
            if (product.isEmpty() || product.get().getStock() < sell.getQty()) return null;

            // Updating the stock
            product.get().setStock(product.get().getStock() - sell.getQty());
            products.add(product.get());

            sell.setProduct(product.get());
            sell.setPrice(sell.getProduct().getPrice()* sell.getQty());
            total += sell.getPrice();
        }

        // Update the stock
        for (Product p : products) productService.updateProduct(p);

        // Setting and saving entities
        bill.setUser(user.get());
        bill.setAmount(total);
        bill.setDate(String.format("dd/MM/yyyy", new Date()));
        userService.save(user.get());
        return billRepository.save(bill);
    }

    public List<Bill> findAll() {
        return billRepository.findAll();
    }
    public Optional<Bill> findById(Long id) {
        return billRepository.findById(id);
    }
    public void delete(Long id) {
        billRepository.deleteById(id);
    }

    public List<Bill> mostExpensiveBills() {
        return billRepository.findAllByOrderByAmountDesc();
    }
}
