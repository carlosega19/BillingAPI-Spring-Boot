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

    @GetMapping(value = "/user={id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Bill> getBillsByUserId(@PathVariable Long id) {
        return billService.findByUserId(id);
    }

    // POSTs
    @PostMapping(value = "/user={id}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Bill> createBill(@RequestBody Bill bill, @PathVariable Long id) {
        billService.save(bill, id);
        return ResponseEntity.ok(bill);
    }

    // DELETEs
    @DeleteMapping(value = "/{id}")
    public void deleteBill(@PathVariable Long id) {
        billService.delete(id);
    }

}