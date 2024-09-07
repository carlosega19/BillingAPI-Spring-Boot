package com.main.controllers;

import com.main.entities.Sell;
import com.main.services.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/sells")
public class SellController {
    @Autowired
    SellService sellService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Sell> getAllSells() {
        return sellService.getAllSells();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sell> getSellById(@PathVariable Long id) {
        Optional<Sell> sell = sellService.getSellById(id);
        if (sell.isPresent()) {
            return ResponseEntity.ok(sell.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/bill={idB}&product={idP}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Sell> createSell(@RequestBody Sell sell, @PathVariable Long idB, @PathVariable Long idP) {
        sellService.saveSell(sell, idB, idP);
        return ResponseEntity.ok(sell);
    }

    //test
    @DeleteMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> deleteSell(@PathVariable Long id) {
        sellService.deleteSellById(id);
        return ResponseEntity.noContent().build();
    }
}
