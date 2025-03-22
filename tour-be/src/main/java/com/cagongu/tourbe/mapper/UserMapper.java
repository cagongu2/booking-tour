package com.cagongu.tourbe.mapper;


import com.cagongu.tourbe.dto.request.UserCreateRequest;
import com.cagongu.tourbe.dto.request.UserUpdateRequest;
import com.cagongu.tourbe.dto.response.UserResponse;
import com.cagongu.tourbe.model.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mapping(target = "idAccount", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "dateAdded", ignore = true)
    @Mapping(target = "dateEdited", ignore = true)
    @Mapping(target = "dateDeleted", ignore = true)
    @Mapping(target = "payed", ignore = true)
    Account userCreationRequestToUser(UserCreateRequest request);

    UserResponse userToUserResponse(Account account);

    @Mapping(target = "idAccount", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "dateAdded", ignore = true)
    @Mapping(target = "dateEdited", ignore = true)
    @Mapping(target = "dateDeleted", ignore = true)
    @Mapping(target = "payed", ignore = true)
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // Bỏ qua các thunk tính null
    void updateUser(@MappingTarget Account account, UserUpdateRequest request);
}
