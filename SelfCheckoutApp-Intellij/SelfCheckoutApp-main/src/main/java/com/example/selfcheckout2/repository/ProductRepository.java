package com.example.selfcheckout2.repository;

import com.example.selfcheckout2.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    Optional<Product> findById(Long aLong);

    Optional<Product> findByBarcode(String barcode);

    @Override
    List<Product> findAll();
}
