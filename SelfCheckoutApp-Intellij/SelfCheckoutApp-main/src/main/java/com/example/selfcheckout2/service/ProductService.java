package com.example.selfcheckout2.service;

import com.example.selfcheckout2.data.Product;
import com.example.selfcheckout2.data.ProductData;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
public interface ProductService {
    ProductData saveProduct(ProductData product);
    void saveProductList(List<ProductData> productList);
    ProductData getProductById(final Long productId);
    ProductData getProductByBarcode(String barcode);
    void retrieveProductsFromAllSupermarkets();

    List<ProductData> getProductsBySupermarketAdministrator(String email);

    Product updateProduct(ProductData productData, Long id) throws ValidationException;

    ProductData getProductByName(String name);
}
