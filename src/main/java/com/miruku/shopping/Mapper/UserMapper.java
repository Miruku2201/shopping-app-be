package com.miruku.shopping.Mapper;

import com.miruku.shopping.Entity.User;
import com.miruku.shopping.dto.Request.UserCreationRequest;
import com.miruku.shopping.dto.Request.UserUpdateRequest;
import com.miruku.shopping.dto.Response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(ignore = true, target = "roles")
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
    @Mapping(ignore = true, target = "roles")
    void toUserUpdate(@MappingTarget User user, UserUpdateRequest request);
}
