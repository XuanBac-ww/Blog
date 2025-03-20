package com.example.BlogBe.service.Category;

import com.example.BlogBe.dto.CategoryDto;
import com.example.BlogBe.model.Category;
import com.example.BlogBe.repository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        if (existCategoryByName(categoryDto.getName())) {
            throw new EntityExistsException("Category name already exists");
        }
        Category category = mapToEntity(categoryDto);
        return categoryRepository.save(category);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category exitsCategory = getCategoryById(id);
        if(!exitsCategory.getName().equals(categoryDto.getName()) && existCategoryByName(categoryDto.getName())) {
            throw new EntityExistsException("Category name already exists");
        }
        exitsCategory.setName(categoryDto.getName());
        Category updatedCategory = categoryRepository.save(exitsCategory);
        return mapToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete,() -> {
                    throw new EntityNotFoundException("Category not found");
                });
    }

    @Override
    public CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    @Override
    public Category mapToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }

    private boolean existCategoryByName(String name) {
        return categoryRepository.existsByName(name);
    }
}
