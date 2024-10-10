package com.miruku.shopping.dto.response;

import com.miruku.shopping.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private String name;

    private LocalDate createDate;

    private LocalDate updateDate;

    private Category parentCategory;

    private List<Category> subCategories;
}
