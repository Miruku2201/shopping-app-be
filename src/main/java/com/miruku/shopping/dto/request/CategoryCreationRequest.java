package com.miruku.shopping.dto.request;

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
    private String parentCategory = null;

    @Builder.Default
    private List<String> subCategories = new ArrayList<>();
}
