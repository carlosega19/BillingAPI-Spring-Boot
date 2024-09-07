package com.main.services;

import com.main.entities.Address;
import com.main.entities.User;
import com.main.repositories.AdressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdressService {
    @Autowired
    AdressRepository adressRepository;

    @Autowired
    private UserService userService;

    public Optional<Address> getAdressById(Long id) {
        return adressRepository.findById(id);
    }
    public Address saveAdress(Address adress, Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) return null;
        adress.setUser(user.get());
        userService.save(user.get());
        return adressRepository.save(adress);
    }
    public Address updateAdress(Address adress) {
        return adressRepository.save(adress);
    }
    public void deleteAdressById(Long id) {
        Address address = adressRepository.findById(id).get();
        if (address == null) return;

        User user = address.getUser();
        user.getAddresses().remove(address);
        userService.update(user);

    }

    public List<Address> getAllAdresses() {
        return adressRepository.findAll();
    }
    public List<Address> getAdressesByUserId(Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return user.get().getAddresses();
        }
        return null;
    }
}
