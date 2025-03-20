package com.example.BlogBe.controller;

import com.example.BlogBe.dto.CategoryDto;
import com.example.BlogBe.model.Category;
import com.example.BlogBe.response.ApiResponse;
import com.example.BlogBe.service.Category.CategoryServiceImpl;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Category")
public class CategoryController {


    private final CategoryServiceImpl categoryServiceImpl;

    public CategoryController(CategoryServiceImpl categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        List<Category> categories = categoryServiceImpl.getAllCategories();
        return ResponseEntity.ok(new ApiResponse("Found",categories));
    }

    @GetMapping("/getCategoryId/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable  Long id) {
        Category category = categoryServiceImpl.getCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("Found",category));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryServiceImpl.createCategory(categoryDto);
        return ResponseEntity.ok(new ApiResponse("Category added successfully", category));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryServiceImpl.updateCategory(id,categoryDto);
        return ResponseEntity.ok(new ApiResponse("Category updated successfully", updatedCategory));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        categoryServiceImpl.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse("Category deleted successfully",null));
    }


}
