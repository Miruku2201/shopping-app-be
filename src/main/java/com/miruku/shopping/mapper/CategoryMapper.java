package com.miruku.shopping.mapper;

import com.miruku.shopping.entity.Category;
import com.miruku.shopping.dto.request.CategoryCreationRequest;
import com.miruku.shopping.dto.request.CategoryUpdateRequest;
import com.miruku.shopping.dto.response.CategoryResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(ignore = true, target = "subCategories")
    @Mapping(ignore = true, target = "parentCategory")
    Category toCategory(CategoryCreationRequest request);

    CategoryResponse toCategoryResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdateCategory(@MappingTarget Category category, CategoryUpdateRequest request);
}
