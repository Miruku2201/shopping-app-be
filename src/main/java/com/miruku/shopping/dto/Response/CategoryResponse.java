package com.miruku.shopping.dto.Response;

import com.miruku.shopping.Entity.Category;
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

    private Long parentCategory;

    private List<Category> subCategories;
}
