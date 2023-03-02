package com.example.selfcheckout.data;

import java.util.Date;
import java.util.List;

public class BillData implements Comparable<BillData>{
    private Long id;
    private List<String> productNames;
    private List<String> prices;
    private List<String> quantities;
    private Long idUser;
    private String total;
    private String date;
    private String supermarket;

    public String getDate()
    {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

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

    public BillData() {
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
    @Override
    public int compareTo(BillData bill) {
        if (getDate() == null || bill.getDate() == null) {
            return 0;
        }
        return getDate().compareTo(bill.getDate());
    }
}
