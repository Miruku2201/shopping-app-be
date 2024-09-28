//package com.miruku.shopping.Entity;
//
//import com.miruku.shopping.Enum.StatusItem;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class Item extends UUIDEntity{
//    @Column(nullable = false)
//    private String name;
//
//    private Long price;
//
//    private Integer stock;
//
//    private String brand;
//
//    private Long categoryID;
//
//    private LocalDate createDate;
//
//    private LocalDate updateDate;
//
//    @Builder.Default
//    private StatusItem status = StatusItem.AVAILABLE;
//}
