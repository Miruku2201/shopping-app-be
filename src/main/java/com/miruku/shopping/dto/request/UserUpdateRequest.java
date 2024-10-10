package com.miruku.shopping.dto.request;

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
