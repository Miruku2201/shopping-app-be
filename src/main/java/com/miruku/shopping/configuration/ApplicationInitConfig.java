package com.miruku.shopping.configuration;


import com.miruku.shopping.entity.Role;
import com.miruku.shopping.entity.User;
import com.miruku.shopping.Enum.RolesEnum;
import com.miruku.shopping.repository.RoleRepository;
import com.miruku.shopping.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

import java.util.Set;

@Configuration
public class ApplicationInitConfig {
    @Autowired
    private static final Logger log = LoggerFactory.getLogger(ApplicationInitConfig.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Bean
    public ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            // CHECK_ROLE
            if (roleRepository.findByName(RolesEnum.ADMIN.name()) == null){
                Role adminRole = Role.builder()
                        .name(RolesEnum.ADMIN.name())
                        .build();
                roleRepository.save(adminRole);
                log.warn("ADMIN is created in Database");
            }

            if (roleRepository.findByName(RolesEnum.USER.name()) == null){
                Role userRole = Role.builder()
                        .name(RolesEnum.USER.name())
                        .build();
                roleRepository.save(userRole);
                log.warn("USER is created in Database");
            }

            // check existed ADMIN
            if (!userRepository.existsByUsername("admin")){
                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .email("admin@example.com")
                        .name("ADMIN")
                        .build();
                Set<Role> roles = new HashSet<>();
                Role adminRole = roleRepository.findByName(RolesEnum.ADMIN.name());
                roles.add(adminRole);
                admin.setRoles(roles);
                userRepository.save(admin);
                log.warn("Admin account is created in Database. USERNAME: admin, PASSWORD: admin");
            }
            else{
                log.warn("Admin account is existed in Database. USERNAME: admin, PASSWORD: admin");
            }
        };
    }
}
