package com.example.selfcheckout2.repository;

import com.example.selfcheckout2.data.Category;
import com.example.selfcheckout2.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Override
    Optional<Category> findById(Long id);

    Optional<Category> findCategoryByName(String name);
}
