package com.miruku.shopping.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Role extends BaseEntity{
    @Column(nullable = false, unique = true, name = "role_name")
    private String name;
}
