package com.miruku.shopping.mapper;

import com.miruku.shopping.entity.User;
import com.miruku.shopping.dto.request.UserCreationRequest;
import com.miruku.shopping.dto.request.UserUpdateRequest;
import com.miruku.shopping.dto.response.UserResponse;
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
