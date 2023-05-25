package com.example.selfcheckout2.data;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String barcode;

    public String name;
    public String description;
    public float quantity;


    public float price;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "supermarket_id", nullable = false)
    public Supermarket supermarket;


    public Supermarket getSupermarket() {
        return supermarket;
    }

    public void setSupermarket(Supermarket supermarket) {
        this.supermarket = supermarket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Product() {
    }

    public Product(String name, String description, float quantity, String barcode, float price, Category category) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.barcode = barcode;
        this.price = price;
        this.category = category;
    }
    public Product(String name, String description, float quantity, String barcode, float price, Supermarket supermarket, Category category) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.barcode = barcode;
        this.price = price;
        this.supermarket = supermarket;
        this.category = category;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
