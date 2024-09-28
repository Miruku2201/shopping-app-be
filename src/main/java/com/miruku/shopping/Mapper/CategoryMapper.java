package com.miruku.shopping.Mapper;

import com.miruku.shopping.Entity.Category;
import com.miruku.shopping.dto.Request.CategoryCreationRequest;
import com.miruku.shopping.dto.Response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(ignore = true, target = "subCategories")
    Category toCategory(CategoryCreationRequest request);

    CategoryResponse toCategoryResponse(Category category);
}
