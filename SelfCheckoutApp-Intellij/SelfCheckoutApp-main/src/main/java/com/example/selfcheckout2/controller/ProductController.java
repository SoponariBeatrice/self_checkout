package com.example.selfcheckout2.controller;

import com.example.selfcheckout2.data.ProductData;
import com.example.selfcheckout2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/auth")
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/product")
    public String addProduct(@RequestBody ProductData product){
        productService.saveProduct(product);
        return product.toString();
    }

       @GetMapping(path = "/product/{barcode}")
    public ProductData getProductByBarcode(@PathVariable("barcode") String barcode)
    {
        System.out.println("PRODUCT = " + productService.getProductByBarcode(barcode).toString());
        return productService.getProductByBarcode(barcode);
    }
}
