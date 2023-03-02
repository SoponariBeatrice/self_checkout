package com.example.selfcheckout;

import com.example.selfcheckout.data.ProductData;

public interface IApiParser {
    public ProductData getProduct(String barcode); // o sa fie o lista
}
