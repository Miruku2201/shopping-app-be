package com.miruku.shopping.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class UUIDEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
}