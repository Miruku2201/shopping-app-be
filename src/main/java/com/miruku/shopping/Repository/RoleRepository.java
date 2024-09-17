package com.miruku.shopping.Repository;

import com.miruku.shopping.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(String name);

}
