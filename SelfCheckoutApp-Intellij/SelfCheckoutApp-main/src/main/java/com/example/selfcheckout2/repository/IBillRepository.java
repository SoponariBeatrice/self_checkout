package com.example.selfcheckout2.repository;

import com.example.selfcheckout2.data.Bill;
import com.example.selfcheckout2.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IBillRepository extends JpaRepository<Bill, Long> {
    @Override
    Optional<Bill> findById(Long aLong);

    @Override
    List<Bill> findAll();

}
