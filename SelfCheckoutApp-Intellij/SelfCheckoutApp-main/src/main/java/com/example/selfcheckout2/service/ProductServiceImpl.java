package com.example.selfcheckout2.service;

import com.example.selfcheckout2.data.Product;
import com.example.selfcheckout2.data.ProductData;
import com.example.selfcheckout2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Override
    public ProductData saveProduct(ProductData product) {
        Product newProduct = new Product(product.name, product.description, product.quantity,product.barcode,product.category,product.price);
        productRepository.save(newProduct);
        return product;
    }

    @Override
    public void saveProductList(List<ProductData> productList) {
        for (ProductData p : productList
             ) {
            Product newProduct = new Product(p.name, p.description, p.quantity,p.barcode,p.category,p.price);
            productRepository.save(newProduct);
        }
    }

    @Override
    public ProductData getProductById(Long productId) {
        return null;
    }

    public ProductData getProductByBarcode(String barcode)
    {
        List<Product> productList = productRepository.findAll();
        System.out.println("BARCODE === " + barcode);
        for (Product p : productList
             ) {
            System.out.println("LIST = " + p.getBarcode());
            if(p.getBarcode().equals(barcode))
            {
                System.out.println("SERVICE === " + new ProductData(p.name, p.description, p.quantity,p.barcode,p.category,p.price));

                return new ProductData(p.name, p.description, p.quantity,p.barcode,p.category,p.price);
            }
        }
        return null;
    }
}
