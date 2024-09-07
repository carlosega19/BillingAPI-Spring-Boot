package com.main.services;

import com.main.entities.Bill;
import com.main.entities.Product;
import com.main.entities.Sell;
import com.main.repositories.BillRepository;
import com.main.repositories.ProductRepository;
import com.main.repositories.SellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellService {
    @Autowired
    private SellRepository sellRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Sell> getAllSells() {
        return sellRepository.findAll();
    }
    public Optional<Sell> getSellById(Long id) {
        return sellRepository.findById(id);
    }
    public Sell saveSell(Sell sell, Long idB, Long idP) {
        Bill bill = billRepository.findById(idB).get();
        Product product = productRepository.findById(idP).get();

        if (bill == null || product == null) return null;

        System.out.println(bill.getDate());
        System.out.println(product.getName());

        sell.setBill(bill);
        sell.setProduct(product);
        sell.setPrice(product.getPrice()*sell.getQty());

        return sellRepository.save(sell);
    }

    // test
    public void deleteSellById(Long id) {
        sellRepository.deleteById(id);
    }

}
