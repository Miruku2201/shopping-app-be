package com.miruku.shopping.dto.Request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String email;

    // Thông tin cá nhân người dùng.
    private String name;
    private LocalDate dob;
}
