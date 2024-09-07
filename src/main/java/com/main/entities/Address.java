package com.main.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String street;
    @Column(length = 50, nullable = false)
    private String city;

    @Column(length = 50, nullable = false)
    private String state;

    @Column(length = 50, nullable = false)
    private String zip;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Address() {}
    public Address(String street, String city, String state, String zip, User user) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.user = user;
    }


    // GETTERS AND SETTERS
    public Long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public String getZip() {
        return zip;
    }
    public String getState() {
        return state;
    }
    public String getCity() {
        return city;
    }
    public String getStreet() {
        return street;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    public void setUser(User user) {
        this.user = user;
    }
}

