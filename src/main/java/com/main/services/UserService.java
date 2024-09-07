package com.main.services;

import com.main.entities.User;
import com.main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Basic methods
    public User save(User user) {
        return userRepository.save(user);
    }
    public User update(User user) {
        return userRepository.save(user);
    }
    public User delete(Long id) {
        Optional<User> selected = findById(id);
        userRepository.delete(selected.get());
        return selected.get();
    }

    // Other methods
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    public Optional<User> findByPassword(String password) {
        return Optional.ofNullable(userRepository.findByPassword(password));
    }
}
