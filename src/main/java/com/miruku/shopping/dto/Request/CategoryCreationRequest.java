package com.miruku.shopping.dto.Request;

import com.miruku.shopping.Entity.Category;
import com.miruku.shopping.Validation.Constraint.DobConstraint;
import jakarta.validation.constraints.Size;
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
public class CategoryCreationRequest {
    private String name;

    private LocalDate createDate;

    private LocalDate updateDate;

    @Builder.Default
    private Category parentCategory = null;

    @Builder.Default
    private List<Category> subCategories = new ArrayList<>();
}
