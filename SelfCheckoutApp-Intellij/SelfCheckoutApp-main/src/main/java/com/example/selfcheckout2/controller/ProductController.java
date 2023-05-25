package com.example.selfcheckout2.controller;

import com.example.selfcheckout2.data.Category;
import com.example.selfcheckout2.data.Product;
import com.example.selfcheckout2.data.ProductData;
import com.example.selfcheckout2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/auth")
@EnableScheduling
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("/product")
    public ProductData addProduct(@RequestBody ProductData product){
        return productService.saveProduct(product);
    }

       @GetMapping(path = "/product/{barcode}")
    public ProductData getProductByBarcode(@PathVariable("barcode") String barcode)
    {
        System.out.println("PRODUCT = " + productService.getProductByBarcode(barcode).toString());
        return productService.getProductByBarcode(barcode);
    }

    @Scheduled(fixedRate = 86400000)
    @GetMapping
    public void retrieveAllProductsFromAllSupermarkets()
    {
        productService.retrieveProductsFromAllSupermarkets();
    }

    @GetMapping("/{email}")
    public List<ProductData> getAllProductsBySupermarketAdministrator(@PathVariable String email){
        System.out.println("email = " + email);
        return productService.getProductsBySupermarketAdministrator(email);
    }

    @PutMapping("/{id}")
    public ProductData update(@RequestBody ProductData productData, @PathVariable Long id) throws ValidationException {
        Product product = productService.updateProduct(productData, id);
        Category category = product.getCategory();
        return new ProductData(product.name, product.description, product.quantity, product.barcode, category.getName(), product.price);
    }

    @GetMapping("/product-by-name/{name}")
    public ProductData getProductByName(@PathVariable String name){
        return productService.getProductByName(name);
    }

}
