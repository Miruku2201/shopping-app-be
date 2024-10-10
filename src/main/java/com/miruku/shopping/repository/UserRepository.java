package com.miruku.shopping.repository;

import com.miruku.shopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
//    @Query("SELECT u FROM User u WHERE u.username = :username")
//    User findByUsername(@Param("username") String username);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
