package com.hsf302_group2.club_management_system.premember.service;

import com.hsf302_group2.club_management_system.common.enums.UserStatus;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.common.mapper.PreMemberMapper;
import com.hsf302_group2.club_management_system.common.mapper.UserMapper;
import com.hsf302_group2.club_management_system.mail.MailService;
import com.hsf302_group2.club_management_system.memberform.dto.response.MemberFormResponse;
import com.hsf302_group2.club_management_system.premember.dto.response.PreMemberResponse;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import com.hsf302_group2.club_management_system.premember.repository.PreMemberRepository;
import com.hsf302_group2.club_management_system.user.dto.request.UserCreationRequest;
import com.hsf302_group2.club_management_system.user.entity.User;
import com.hsf302_group2.club_management_system.user.repository.UserRepository;
import com.hsf302_group2.club_management_system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class PreMemberService {
    PreMemberMapper preMemberMapper;
    PreMemberRepository preMemberRepository;

    public PreMember getPreMemberResponseByToken() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        PreMemberResponse preMemberResponse = preMemberRepository.findPreMemberByToken(email)
                .orElseThrow(() -> new AppException(ErrorCode.PRE_MEMBER_NOT_EXISTED));
        return preMemberRepository.findById(preMemberResponse.getPreMemberId()).orElseThrow(() -> new AppException(ErrorCode.PRE_MEMBER_NOT_EXISTED));

    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PreMemberResponse> getAllPreMembers() {
        return preMemberRepository.findAll().stream()
                .map(preMemberMapper::toPreMemberResponse)
                .collect(Collectors.toList());
    }



}
