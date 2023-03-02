package com.example.selfcheckout;

public class ChartItem {
    String name;
    float quantity;
    float price;
    float priceForSelectedQuantity;

    public ChartItem(String name, float quantity, float price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.priceForSelectedQuantity = price;
    }

    public float getPriceForSelectedQuantity() {
        return priceForSelectedQuantity;
    }

    public void setPriceForSelectedQuantity(float priceForSelectedQuantity) {
        this.priceForSelectedQuantity = priceForSelectedQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
