package com.miruku.shopping.Mapper;

import com.miruku.shopping.Entity.Category;
import com.miruku.shopping.dto.Request.CategoryCreationRequest;
import com.miruku.shopping.dto.Request.CategoryUpdateRequest;
import com.miruku.shopping.dto.Response.CategoryResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
//    @Mapping(ignore = true, target = "subCategories")
    Category toCategory(CategoryCreationRequest request);

    CategoryResponse toCategoryResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdateCategory(@MappingTarget Category category, CategoryUpdateRequest request);
}
