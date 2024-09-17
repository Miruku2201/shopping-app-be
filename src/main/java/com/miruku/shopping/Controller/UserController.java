package com.miruku.shopping.Controller;

import com.miruku.shopping.Entity.User;
import com.miruku.shopping.Repository.UserRepository;
import com.miruku.shopping.Service.UserService;
import com.miruku.shopping.dto.Request.UserCreationRequest;
import com.miruku.shopping.dto.Request.UserUpdateRequest;
import com.miruku.shopping.dto.Response.ApiResponse;
import com.miruku.shopping.dto.Response.UserResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUsers(){
        var authenticate = SecurityContextHolder.getContext().getAuthentication();
        log.warn(authenticate.getAuthorities().toString());
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    @GetMapping(value = "/{id}")
    ApiResponse<UserResponse> getUser(@PathVariable("id") String id){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(id))
                .build();
    }

    @PutMapping(value = "/update/{id}")
    ApiResponse<UserResponse> updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id, request))
                .build();
    }

    @DeleteMapping(value = "/delete/{id}")
    ApiResponse<String> deleteUser(@PathVariable("id") String id){
        return ApiResponse.<String>builder()
                .result(userService.deleteUser(id))
                .build();
    }
}
