package com.hsf302_group2.club_management_system.user.service;

import com.hsf302_group2.club_management_system.common.enums.Role;
import com.hsf302_group2.club_management_system.common.enums.UserStatus;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.common.mapper.UserMapper;
import com.hsf302_group2.club_management_system.user.dto.request.UserCreationRequest;
import com.hsf302_group2.club_management_system.user.dto.response.UserResponse;
import com.hsf302_group2.club_management_system.user.entity.User;
import com.hsf302_group2.club_management_system.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class UserService {
    UserMapper userMapper;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser (UserCreationRequest request, String role){
        if(isEmailExisted(request.getEmail())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        User user =  userMapper.toUser(request);
        user.setRole(role);
        user.setStatus(UserStatus.ACTIVE.name());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public boolean isEmailExisted(String email){
        return userRepository.existsByEmail(email);
    }

    public List<UserResponse> getAllUser (){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    public List<UserResponse> getAllUsers(Role role) {
        if (role == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        String roleName = role.name();
        return userRepository.findByRole(roleName).stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
