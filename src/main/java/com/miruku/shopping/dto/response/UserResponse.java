package com.miruku.shopping.dto.response;

import com.miruku.shopping.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class UserResponse {
    private String username;

//    private String password;

    private String email;

    // Thông tin cá nhân người dùng.
    private String name;
    private LocalDate dob;
    private Set<Role> roles;
}
