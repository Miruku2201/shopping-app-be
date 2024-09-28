package com.miruku.shopping.Controller;


import com.miruku.shopping.Entity.Category;
import com.miruku.shopping.Service.CategoryService;
import com.miruku.shopping.dto.Request.CategoryCreationRequest;
import com.miruku.shopping.dto.Response.ApiResponse;
import com.miruku.shopping.dto.Response.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // Get Category by ID

    // Update Category

    // Delete Category
}
