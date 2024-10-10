package com.miruku.shopping.controller;


import com.miruku.shopping.entity.Category;
import com.miruku.shopping.service.CategoryService;
import com.miruku.shopping.dto.request.CategoryAddingRequest;
import com.miruku.shopping.dto.request.CategoryCreationRequest;
import com.miruku.shopping.dto.request.CategoryUpdateRequest;
import com.miruku.shopping.dto.response.ApiResponse;
import com.miruku.shopping.dto.response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    // Create new category
    @PostMapping
    ApiResponse<CategoryResponse> createCategory (@RequestBody CategoryCreationRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(request))
                .build();
    }
    // Get All category
    @GetMapping
    ApiResponse<List<Category>> getAllCategories(){
        return ApiResponse.<List<Category>>builder()
                .result(categoryService.getAllCategories())
                .build();
    }
    // Get Category by ID
    @GetMapping("/{id}")
    ApiResponse<CategoryResponse> getCategoryById(@PathVariable("id") Long id){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getCategoryById(id))
                .build();
    }

    // Update Category
    @PutMapping("/{id}")
    ApiResponse<CategoryResponse> updateCategoryById(@PathVariable("id") Long id, @RequestBody CategoryUpdateRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategoryById(id, request))
                .build();
    }

    // Delete Category
    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteCategoryById(@PathVariable("id") Long id){
        return ApiResponse.<Void>builder()
                .result(categoryService.deleteCategoryById(id))
                .build();
    }

    // add 1 Cate -> Cate
    @PutMapping("/add/{id}")
    ApiResponse<CategoryResponse> addCategoryToOne(@PathVariable("id") Long addID, @RequestBody CategoryAddingRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.addCategoryToOne(addID, request))
                .build();
    }
}
