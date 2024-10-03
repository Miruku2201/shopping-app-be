package com.miruku.shopping.Service;

import com.miruku.shopping.Entity.Category;
import com.miruku.shopping.Exception.AppException;
import com.miruku.shopping.Exception.CategoryErrorCode;
import com.miruku.shopping.Mapper.CategoryMapper;
import com.miruku.shopping.Repository.CategoryRepository;
import com.miruku.shopping.dto.Request.CategoryCreationRequest;
import com.miruku.shopping.dto.Request.CategoryUpdateRequest;
import com.miruku.shopping.dto.Response.CategoryResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    public CategoryResponse createCategory(@NotNull CategoryCreationRequest request){
        if(categoryRepository.existsByName(request.getName())){
            throw new AppException(CategoryErrorCode.CATEGORY_NAME_EXISTED);
        }

        Category category = categoryMapper.toCategory(request);
        category.setSubCategories(request.getSubCategories());

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public List<CategoryResponse> getAllCategories(){
        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        for(Category category : categoryRepository.findAll()){
            categoryResponseList.add(categoryMapper.toCategoryResponse(category));
        }
        return categoryResponseList;
    }

    public CategoryResponse getCategoryById(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(CategoryErrorCode.NOT_EXISTED_CATEGORY));

        return categoryMapper.toCategoryResponse(category);
    }

    public CategoryResponse updateCategoryById(Long id, CategoryUpdateRequest request){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(CategoryErrorCode.NOT_EXISTED_CATEGORY));
        categoryMapper.toUpdateCategory(category, request);
        return categoryMapper.toCategoryResponse(category);
    }

    public Void deleteCategoryById(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new AppException(CategoryErrorCode.NOT_EXISTED_CATEGORY));

        if (category.getSubCategories() != null && !category.getSubCategories().isEmpty()){
            category.getSubCategories().forEach(subCategory ->
                    {subCategory.setParentCategory(null);
                        categoryRepository.save(subCategory);
                    });
        }

        categoryRepository.delete(category);
        return null;
    }
}
