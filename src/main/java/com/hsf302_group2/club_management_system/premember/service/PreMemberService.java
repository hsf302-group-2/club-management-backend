package com.hsf302_group2.club_management_system.premember.service;

import com.hsf302_group2.club_management_system.common.enums.UserStatus;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.common.mapper.PreMemberMapper;
import com.hsf302_group2.club_management_system.common.mapper.UserMapper;
import com.hsf302_group2.club_management_system.mail.MailService;
import com.hsf302_group2.club_management_system.memberform.dto.response.MemberFormResponse;
import com.hsf302_group2.club_management_system.premember.dto.request.PreMemberUpdatePasswordRequest;
import com.hsf302_group2.club_management_system.premember.dto.request.PreMemberUpdateRequest;
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
    UserService userService;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;

    public PreMember getPreMemberResponseByToken() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        PreMemberResponse preMemberResponse = preMemberRepository.findPreMemberByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.PRE_MEMBER_NOT_EXISTED));
        return preMemberRepository.findById(preMemberResponse.getPreMemberId())
                .orElseThrow(() -> new AppException(ErrorCode.PRE_MEMBER_NOT_EXISTED));

    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PreMemberResponse> getAllPreMembers() {
        return preMemberRepository.findAll().stream()
                .map(preMemberMapper::toPreMemberResponse)
                .collect(Collectors.toList());
    }

    public PreMemberResponse getPreMemberProfileByToken() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        return preMemberRepository.findPreMemberByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.PRE_MEMBER_NOT_EXISTED));
    }

    @PreAuthorize("hasRole('PRE_MEMBER')")
    public boolean changePassword(PreMemberUpdatePasswordRequest request) {
        PreMemberResponse preMemberResponse = getPreMemberProfileByToken();
        User user = userService.findUserByEmail(preMemberResponse.getEmail());
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCHES);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        if (request.getNewPassword().equals(request.getOldPassword())) {
            throw new AppException(ErrorCode.PASSWORD_DUPLICATED);
        }
        userRepository.save(user);
        return true;
    }

    @PreAuthorize("hasRole('PRE_MEMBER')")
    public PreMemberResponse updatePreMemberProfile(PreMemberUpdateRequest request){
        PreMember preMember = getPreMemberResponseByToken();
        preMemberMapper.updatePreMember(preMember, request);
        return preMemberMapper.toPreMemberResponse(preMemberRepository.save(preMember));
    }

    @PreAuthorize("hasRole('PRE_MEMBER')")
    public PreMemberResponse getPreMemberProfile(){
        PreMember preMember = getPreMemberResponseByToken();
        return preMemberMapper.toPreMemberResponse(preMember);
    }



}
