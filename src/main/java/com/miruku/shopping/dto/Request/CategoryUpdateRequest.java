package com.miruku.shopping.dto.Request;

import com.miruku.shopping.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryUpdateRequest {
    private String name;

    private LocalDate createDate;

    private LocalDate updateDate;

    private Category parentCategory;

    private List<Category> subCategories;
}
