package com.miruku.shopping.Service;

import com.miruku.shopping.Entity.Role;
import com.miruku.shopping.Entity.User;
import com.miruku.shopping.Enum.RolesEnum;
import com.miruku.shopping.Exception.AppException;
import com.miruku.shopping.Exception.ErrorCode;
import com.miruku.shopping.Mapper.UserMapper;
import com.miruku.shopping.Repository.RoleRepository;
import com.miruku.shopping.Repository.UserRepository;
import com.miruku.shopping.dto.Request.UserCreationRequest;
import com.miruku.shopping.dto.Request.UserUpdateRequest;
import com.miruku.shopping.dto.Response.UserResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new AppException(ErrorCode.EXISTED_USERNAME);
        }
        User newUserCreated = userMapper.toUser(request);

        // encode password
        newUserCreated.setPassword(passwordEncoder.encode(newUserCreated.getPassword()));

        // Set role "USER" for new user
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleRepository.findByName(RolesEnum.USER.name()));
        newUserCreated.setRoles(userRoles);
        //
//        return userMapper.toUserResponse(userRepository.save(newUserCreated));
        return userMapper.toUserResponse(userRepository.save(newUserCreated));
    }

    public List<UserResponse> getAllUsers(){
        List<UserResponse> listUserResponse = new ArrayList<>();
        for(User u : userRepository.findAll()){
            listUserResponse.add(userMapper.toUserResponse(u));
        }
        return listUserResponse;
    }

    public UserResponse getUser(String id){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.EXISTED_USERNAME));
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String id,UserUpdateRequest request){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.EXISTED_USERNAME));
        userMapper.toUserUpdate(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public String deleteUser(String id){
        userRepository.deleteById(id);
        return "User with id: " + id + " has been deleted";
    }
}