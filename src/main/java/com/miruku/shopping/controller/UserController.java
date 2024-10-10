package com.miruku.shopping.controller;

import com.miruku.shopping.service.UserService;
import com.miruku.shopping.dto.request.UserCreationRequest;
import com.miruku.shopping.dto.request.UserUpdateRequest;
import com.miruku.shopping.dto.response.ApiResponse;
import com.miruku.shopping.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PutMapping(value = "/{id}")
    ApiResponse<UserResponse> updateUser(@PathVariable("id") String id, @RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id, request))
                .build();
    }

    @DeleteMapping(value = "/{id}")
    ApiResponse<String> deleteUser(@PathVariable("id") String id){
        return ApiResponse.<String>builder()
                .result(userService.deleteUser(id))
                .build();
    }
}
