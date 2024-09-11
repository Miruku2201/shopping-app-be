package com.miruku.shopping.dto.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserResponse {
    private String username;

//    private String password;

    private String email;

    // Thông tin cá nhân người dùng.
    private String name;
    private LocalDate dob;
//    private String roles;
}
