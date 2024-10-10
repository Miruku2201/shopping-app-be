package com.miruku.shopping.dto.response;

import com.miruku.shopping.Enum.StatusItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {
    private String name;

    private Long price;

    private Integer stock;

    private String brand;

    private LocalDate createDate;

    private LocalDate updateDate;

    private String userCreatedId;

    private StatusItem status;
}
