package com.miruku.shopping.entity;

import com.miruku.shopping.Enum.StatusItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item extends UUIDEntity{
    private String name;

    private Long price;

    private Integer stock;

    private String brand;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    private StatusItem status;

    @Column(name = "user_created_id")
    private String userCreatedId;
}
