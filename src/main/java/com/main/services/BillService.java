package com.main.services;

import com.main.entities.Bill;
import com.main.entities.Sell;
import com.main.entities.User;
import com.main.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) return null;

        double total = 0;
        for (Sell sell : bill.getSells()) {
            sell.setBill(bill);
            sell.setProduct(productService.getProductById(sell.getProduct().getId()).get());
            sell.setPrice(sell.getProduct().getPrice()* sell.getQty());
            total += sell.getPrice();
        }


        bill.setUser(user.get());
        bill.setAmount(total);
        userService.save(user.get());

        return billRepository.save(bill);
    }
    public List<Bill> findAll() {
        return billRepository.findAll();
    }
    public Optional<Bill> findById(Long id) {
        return billRepository.findById(id);
    }

    public List<Bill> findByUserId(Long id) {
        return billRepository.findBillsByUserId(id);
    }

    public void delete(Long id) {
        billRepository.deleteById(id);
    }
}
