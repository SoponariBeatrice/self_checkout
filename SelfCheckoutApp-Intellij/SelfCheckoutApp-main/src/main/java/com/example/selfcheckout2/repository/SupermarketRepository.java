package com.example.selfcheckout2.repository;

import com.example.selfcheckout2.data.Product;
import com.example.selfcheckout2.data.Supermarket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupermarketRepository extends JpaRepository<Supermarket, Long> {
    @Override
    Optional<Supermarket> findById(Long aLong);

    @Override
    List<Supermarket> findAll();

    Supermarket findSupermarketByEmailContact(String email);
}
