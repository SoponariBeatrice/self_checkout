package com.example.selfcheckout2.controller;

import com.example.selfcheckout2.data.ProductData;
import com.example.selfcheckout2.data.Supermarket;
import com.example.selfcheckout2.data.SupermarketData;
import com.example.selfcheckout2.service.ProductService;
import com.example.selfcheckout2.service.SupermarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
@RestController
public class SupermarketController {
    @Autowired
     SupermarketService service;

    @PostMapping("/supermarket")
    public void addProduct(@RequestBody SupermarketData supermarketData){
        service.saveSupermarket(supermarketData);
    }
    @GetMapping("/all-supermarkets")
    public List<SupermarketData> getSupermarkets()
    {
        return service.getAll();
    }
}
