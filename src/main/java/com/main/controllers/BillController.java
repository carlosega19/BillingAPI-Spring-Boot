package com.main.controllers;

import com.main.entities.Bill;
import com.main.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/bills")
public class BillController {
    @Autowired
    private BillService billService;

    // GETs
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Bill> getAllBills() {
        return billService.findAll();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
        Optional<Bill> bill = billService.findById(id);
        if (bill.isPresent()) {
            return ResponseEntity.ok(bill.get());
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping(value = "/betters", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Bill>> getBestBills() {
        List<Bill> bills = billService.mostExpensiveBills();
        if (bills.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bills);
    }

    // POSTs
    @PostMapping(value = "/user={id}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill, @PathVariable Long id) {
        Bill res = billService.save(bill, id);
        if (res == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bill);
    }

    // DELETEs
    @DeleteMapping(value = "/{id}")
    public void deleteBill(@PathVariable Long id) {
        billService.delete(id);
    }

}