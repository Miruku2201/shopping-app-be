package com.miruku.shopping.Mapper;

import com.miruku.shopping.Entity.User;
import com.miruku.shopping.dto.Request.UserCreationRequest;
import com.miruku.shopping.dto.Request.UserUpdateRequest;
import com.miruku.shopping.dto.Response.UserResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(ignore = true, target = "roles")
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
    @Mapping(ignore = true, target = "roles")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUserUpdate(@MappingTarget User user, UserUpdateRequest request);
}
