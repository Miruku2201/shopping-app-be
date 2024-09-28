package com.miruku.shopping.Service;

import com.miruku.shopping.Entity.Category;
import com.miruku.shopping.Exception.AppException;
import com.miruku.shopping.Exception.CategoryErrorCode;
import com.miruku.shopping.Mapper.CategoryMapper;
import com.miruku.shopping.Repository.CategoryRepository;
import com.miruku.shopping.dto.Request.CategoryCreationRequest;
import com.miruku.shopping.dto.Response.CategoryResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
