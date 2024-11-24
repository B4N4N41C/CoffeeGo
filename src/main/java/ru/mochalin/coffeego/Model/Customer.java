package ru.mochalin.coffeego.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String phone;
    private String password;
    private String email;
    private boolean isCourier;
    private boolean isAdmin;
    @OneToMany(mappedBy = "сustomer")
    private List<Receipt> receipts;
    @OneToMany(mappedBy = "сustomer")
    private List<Product> products;
}
