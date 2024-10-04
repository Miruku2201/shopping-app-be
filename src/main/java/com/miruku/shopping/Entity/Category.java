package com.miruku.shopping.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miruku.shopping.Enum.StatusCategory;
import com.miruku.shopping.Enum.StatusItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category extends BaseEntity{
    @Column(nullable = false)
    private String name;

    private LocalDate createDate;

    private LocalDate updateDate;

    @Builder.Default
    private StatusCategory status = StatusCategory.ACTIVE;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ParentCategoryID")
    private Category parentCategory;

    @Column(nullable = false)
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> subCategories;
}
