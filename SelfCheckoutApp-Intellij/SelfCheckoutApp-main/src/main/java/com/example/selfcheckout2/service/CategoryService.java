package com.example.selfcheckout2.service;

import com.example.selfcheckout2.data.Category;
import com.example.selfcheckout2.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Category getById(Long id)
    {
        return categoryRepository.findById(id).get();
    }
    public List<Category> getCategories()
    {
        return categoryRepository.findAll();
    }
}
