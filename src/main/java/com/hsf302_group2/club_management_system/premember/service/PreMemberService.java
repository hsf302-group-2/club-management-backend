package com.hsf302_group2.club_management_system.premember.service;

import com.hsf302_group2.club_management_system.common.enums.UserStatus;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.common.mapper.PreMemberMapper;
import com.hsf302_group2.club_management_system.common.mapper.UserMapper;
import com.hsf302_group2.club_management_system.premember.dto.response.PreMemberResponse;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import com.hsf302_group2.club_management_system.premember.repository.PreMemberRepository;
import com.hsf302_group2.club_management_system.user.dto.request.UserCreationRequest;
import com.hsf302_group2.club_management_system.user.entity.User;
import com.hsf302_group2.club_management_system.user.repository.UserRepository;
import com.hsf302_group2.club_management_system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class PreMemberService {
    UserMapper userMapper;
    UserService userService;
    PasswordEncoder passwordEncoder;
    PreMemberMapper preMemberMapper;
    PreMemberRepository preMemberRepository;

    public PreMemberResponse createPreMember(UserCreationRequest request, String role) {
        PreMember preMember = new PreMember();
        User user = userMapper.toUser(request);
        if (userService.isEmailExisted(user.getEmail())) {
            throw new RuntimeException(ErrorCode.EMAIL_EXISTED.getMessage());
        }

        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE.name());
        preMember.setUser(user);
        return preMemberMapper.toPreMemberResponse(preMemberRepository.save(preMember));
    }

}
