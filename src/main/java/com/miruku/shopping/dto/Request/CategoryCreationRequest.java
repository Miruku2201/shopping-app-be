package com.miruku.shopping.dto.Request;

import com.miruku.shopping.Entity.Category;
import com.miruku.shopping.Validation.Constraint.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreationRequest {
    private String name;

    private LocalDate createDate;

    private LocalDate updateDate;

    private Long parentCategory;

    private List<Category> subCategories;
}
