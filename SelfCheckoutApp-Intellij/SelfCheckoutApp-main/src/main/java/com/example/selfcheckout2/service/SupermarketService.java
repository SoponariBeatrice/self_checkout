package com.example.selfcheckout2.service;

import com.example.selfcheckout2.data.ProductData;
import com.example.selfcheckout2.data.Supermarket;
import com.example.selfcheckout2.data.SupermarketData;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SupermarketService {
    void saveSupermarket(SupermarketData supermarket);
    SupermarketData getSupermarketById(final Long supermarketId);
    List<SupermarketData> getAll();

    List<SupermarketData> getSupermarketsByKeyword(String keyword);

    SupermarketData getSupermarketByAdministrator(String administratorEmail);

    SupermarketData updateSupermarket(SupermarketData supermarketData, Long id);
}
