package com.example.selfcheckout.data;

public class ProductData {
    private Long id;
    String name;
    String description;
    float quantity;
    String barcode;
    String category;
    float price;

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

    public ProductData() {
    }

    public ProductData(String name, String description, float quantity, String barcode, String category, float price) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.barcode = barcode;
        this.category = category;
        this.price = price;
    }


    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                " name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", barcode='" + barcode + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
