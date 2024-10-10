package com.miruku.shopping.service;

import com.miruku.shopping.entity.Role;
import com.miruku.shopping.entity.User;
import com.miruku.shopping.Enum.RolesEnum;
import com.miruku.shopping.exception.AppException;
import com.miruku.shopping.exception.AuthenticateErrorCode;
import com.miruku.shopping.mapper.UserMapper;
import com.miruku.shopping.repository.RoleRepository;
import com.miruku.shopping.repository.UserRepository;
import com.miruku.shopping.dto.request.UserCreationRequest;
import com.miruku.shopping.dto.request.UserUpdateRequest;
import com.miruku.shopping.dto.response.UserResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public UserResponse createUser(@NotNull UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new AppException(AuthenticateErrorCode.EXISTED_USERNAME);
        }
        User newUserCreated = userMapper.toUser(request);

        // encode password
        newUserCreated.setPassword(passwordEncoder.encode(newUserCreated.getPassword()));

        // Set role "USER" for new user
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepository.findByName(RolesEnum.USER.name()));
        newUserCreated.setRoles(userRoles);
        //
        UserResponse userResp = userMapper.toUserResponse(userRepository.save(newUserCreated));
        userResp.setRoles(userRoles);
        return userResp;
    }

    public List<UserResponse> getAllUsers(){
        List<UserResponse> listUserResponse = new ArrayList<>();
        for(User u : userRepository.findAll()){
            UserResponse userResp = userMapper.toUserResponse(u);
            userResp.setRoles(u.getRoles());
            listUserResponse.add(userResp);
        }
        return listUserResponse;
//        return userRepository.findAll();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(AuthenticateErrorCode.EXISTED_USERNAME));
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String id,UserUpdateRequest request){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(AuthenticateErrorCode.EXISTED_USERNAME));
        userMapper.toUserUpdate(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public String deleteUser(String id){
        userRepository.deleteById(id);
        return "User with id: " + id + " has been deleted";
    }
}
