package com.cagongu.tourbe.service;


import com.cagongu.tourbe.dto.request.UserCreateRequest;
import com.cagongu.tourbe.dto.request.UserUpdateRequest;
import com.cagongu.tourbe.dto.response.UserResponse;

import java.util.List;


public interface UserService {
    List<UserResponse> listAll();
    UserResponse read(Long id);
    UserResponse readByUsername(String username);
    UserResponse readByEmail(String email);
    UserResponse create(UserCreateRequest request);
    UserResponse update(Long id, UserUpdateRequest request);
    void delete(Long id);

}
