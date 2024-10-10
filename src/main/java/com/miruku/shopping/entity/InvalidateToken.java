package com.miruku.shopping.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class InvalidateToken {
    @Id
    @Column(name = "token-id")
    private String tokenId;

    @Column(name = "invalid-time")
    private LocalDate invalidTime;
}
