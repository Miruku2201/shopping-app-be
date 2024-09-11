package com.miruku.shopping.Repository;

import com.miruku.shopping.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
//    @Query("SELECT u FROM User u WHERE u.username = :username")
//    User findByUsername(@Param("username") String username);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
