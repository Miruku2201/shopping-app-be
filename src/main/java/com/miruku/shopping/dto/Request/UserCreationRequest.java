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
public class UserCreationRequest {
    @Size(min = 4, message = "INVALID_USERNAME")
    private String username;

    @Size(min = 8, message = "INVALID_PASSWORD")
    private String password;

    private String email;

    // Thông tin cá nhân người dùng.
    private String name;
    private LocalDate dob;
}
