package com.miruku.shopping.dto.request;

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
public class ItemCreationRequest {
    private String name;

    private Long price;

    private Integer stock;

    private String brand;

    private LocalDate createDate;

    private LocalDate updateDate;

    @Builder.Default
    private StatusItem status = StatusItem.AVAILABLE;

    private String userCreatedId;
}
