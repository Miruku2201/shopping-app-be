package com.miruku.shopping.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User extends UUIDEntity{
    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(nullable = false, name = "email")
    private String email;

    // Thông tin cá nhân người dùng.
    private String name;
    private LocalDate dob;

    @Column(nullable = false)
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
