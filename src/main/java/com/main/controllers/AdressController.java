package com.main.controllers;

import com.main.entities.Address;
import com.main.services.AdressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/addresses")
public class AdressController {

    @Autowired
    private AdressService adressService;

    // GETs
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Address> getAllAdresses() {
        return adressService.getAllAdresses();
    }
    @GetMapping(value = "/user={id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Address> getUserAdresses(@PathVariable Long id) {
        return adressService.getAdressesByUserId(id);
    }
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAdressById(@PathVariable Long id) {
        Optional<Address> adress = adressService.getAdressById(id);
        if (adress.isPresent()) {
            return ResponseEntity.ok(adress.get());
        }
        return ResponseEntity.notFound().build();
    }

    // POSTs
    @PostMapping(value = "/user={id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> addAdress(@RequestBody Address adress, @PathVariable Long id) {
        adressService.saveAdress(adress, id);
        return ResponseEntity.ok().build();
    }


    // PUTs
    // fix
    @PutMapping(value = "/up", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateAdress(@RequestBody Address adress) {
        adressService.updateAdress(adress);
        return ResponseEntity.ok().build();
    }

    // DELETEs
    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteAdress(@PathVariable Long id) {
        adressService.deleteAdressById(id);
        return ResponseEntity.ok().build();
    }
}