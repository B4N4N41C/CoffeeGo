package ru.mochalin.coffeego.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private int price;
    @ManyToMany
    private List<Ingredient> ingredient;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "сustomer_id")
    private Customer сustomer;
}
