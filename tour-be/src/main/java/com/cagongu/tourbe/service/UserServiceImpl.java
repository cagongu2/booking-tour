package com.cagongu.tourbe.service;

import com.cagongu.tourbe.dto.request.UserCreateRequest;
import com.cagongu.tourbe.dto.request.UserUpdateRequest;
import com.cagongu.tourbe.dto.response.UserResponse;
import com.cagongu.tourbe.enums.Role;
import com.cagongu.tourbe.exception.AppException;
import com.cagongu.tourbe.exception.ErrorCode;
import com.cagongu.tourbe.mapper.UserMapper;
import com.cagongu.tourbe.model.Account;
import com.cagongu.tourbe.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final AccountRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> listAll() {
        log.info("Get all user is ran");
        List<Account> accounts = userRepository.findAll();
        List<UserResponse> response = accounts.stream().map(userMapper::userToUserResponse).toList();
        return response;
    }

    @Override
    public UserResponse read(Long id) {
        log.info("Get by id is ran");
        return userMapper.userToUserResponse(userRepository.findById(id).orElseThrow());
    }

    @Override
    public UserResponse readByUsername(String username) {
        log.info("Get by username is ran");
        return userMapper.userToUserResponse(userRepository.findUserByUsername(username).orElseThrow());
    }

    @Override
    public UserResponse readByEmail(String email) {
        log.info("Get by email is ran");
        return userMapper.userToUserResponse(userRepository.findUserByEmail(email));
    }

    @Override
    public UserResponse create(UserCreateRequest request) {
        log.info("Create new user is ran");
            if(userRepository.findUserByUsername(request.getUsername()).isEmpty()){
                Account account = userMapper.userCreationRequestToUser(request);
                account.setPassword(passwordEncoder.encode(request.getPassword()));

                account.setRole(Role.USER.name());
                return userMapper.userToUserResponse(userRepository.save(account));
            }
            else {
                throw new AppException(ErrorCode.USER_EXISTED);
            }
    }

    @Override
    public UserResponse update(Long id, UserUpdateRequest request) {
        log.info("Update user is ran");
        Account account = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUser(account, request);

        if (StringUtils.hasText(request.getPassword())) {
            account.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (StringUtils.hasText(request.getRole()) && !request.getRole().equals(account.getRole())) {
            account.setRole(request.getRole());
        }

        userRepository.save(account);

        return userMapper.userToUserResponse(account);
    }

    @Override
    public void delete(Long id) {
        log.info("Delete user is ran");
        var user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userRepository.deleteById(user.getIdAccount());
    }
}
