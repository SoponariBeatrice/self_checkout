package com.example.selfcheckout2.data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class BillData {

    private Long id;
    @ElementCollection
    private List<String> productNames;
    @ElementCollection
    private List<String> prices;
    @ElementCollection
    private List<String> quantities;
    private Long idUser;
    private String total;
    private String date;
    private String supermarket;

    public String getSupermarket() {
        return supermarket;
    }

    public void setSupermarket(String supermarket) {
        this.supermarket = supermarket;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    public List<String> getPrices() {
        return prices;
    }

    public void setPrices(List<String> prices) {
        this.prices = prices;
    }

    public List<String> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<String> quantities) {
        this.quantities = quantities;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public BillData(List<String> productNames, List<String> prices, List<String> quantities, Long idUser, String total, String date, String supermarket) {
        this.productNames = productNames;
        this.prices = prices;
        this.quantities = quantities;
        this.idUser = idUser;
        this.total = total;
        this.date = date;
        this.supermarket = supermarket;
    }
}
