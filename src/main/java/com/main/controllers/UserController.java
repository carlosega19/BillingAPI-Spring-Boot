package com.main.controllers;

import com.main.entities.Address;
import com.main.entities.Bill;
import com.main.entities.User;
import com.main.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/users")
@Tag(name = "clients", description = "Relates clients operations")
public class UserController {

    @Autowired
    private UserService userService;

    // GETs
    @Operation(summary = "Get all clients", description = "Return all clients in the database", method = "GET")
    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getAllUsers() {
        List<User> users = userService.findAll();
        return users;
    }

    @Operation(summary = "Get client by ID", description = "Search a client by ID in the database", method = "GET")
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get client by username", description = "Search a client by username in the database", method = "GET")
    @GetMapping(value = "/name={username}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get client by email", description = "Search a client by email in the database", method = "GET")
    @GetMapping(value = "/email={email}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Get client by password", description = "Search a client by password in the database", method = "GET")
    @GetMapping(value = "/pass={password}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserByPassword(@PathVariable String password) {
        Optional<User> user = userService.findByPassword(password);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get client bills by ID", description = "Search all the bills of a client searched by ID in the database", method = "GET")
    @GetMapping(value = "/{id}/bills", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserBills(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get().getBills(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // POSTs
    @Operation(summary = "Add a client", description = "Add new client to the database\nOnly needs the client properties (bills and adress = [])", method = "POST")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createUser(@RequestBody User user) {
		for (Address address : user.getAddresses()) {
			address.setUser(user);
		}
        for (Bill bill : user.getBills()) {
            bill.setUser(user);
        }
        userService.save(user);
        return ResponseEntity.ok(user);
    }
    
    // PUTs
    @Operation(summary = "Update client", description = "Update a client (recive the whole JSON)", method = "PUT")
    @PutMapping(value = "/up", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok(user);
    }

    // DELETEs
    @Operation(summary = "Delete a client by ID", description = "Delete a client by ID from the database", method = "DELETE")
    @DeleteMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userService.delete(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}