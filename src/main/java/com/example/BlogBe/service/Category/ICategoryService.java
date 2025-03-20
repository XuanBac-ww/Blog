package com.example.BlogBe.service.Category;

import com.example.BlogBe.dto.CategoryDto;
import com.example.BlogBe.model.Category;
import com.example.BlogBe.model.Tag;

import java.util.List;

public interface ICategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    void deleteCategory(Long id);

    CategoryDto mapToDto(Category category);

    Category mapToEntity(CategoryDto categoryDto);
}
