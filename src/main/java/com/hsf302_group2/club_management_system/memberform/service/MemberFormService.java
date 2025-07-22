package com.hsf302_group2.club_management_system.memberform.service;

import com.hsf302_group2.club_management_system.clubmember.entity.ClubMember;
import com.hsf302_group2.club_management_system.clubmember.repository.ClubMemberRepository;
import com.hsf302_group2.club_management_system.common.enums.FormStatus;
import com.hsf302_group2.club_management_system.common.enums.Role;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.common.mapper.MemberFormMapper;
import com.hsf302_group2.club_management_system.mail.MailService;
import com.hsf302_group2.club_management_system.memberform.dto.request.MemberFormCreationRequest;
import com.hsf302_group2.club_management_system.memberform.dto.request.MemberFormReviewRequest;
import com.hsf302_group2.club_management_system.memberform.dto.response.MemberFormResponse;
import com.hsf302_group2.club_management_system.memberform.entity.MemberForm;
import com.hsf302_group2.club_management_system.memberform.repository.MemberFormRepository;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import com.hsf302_group2.club_management_system.premember.service.PreMemberService;
import com.hsf302_group2.club_management_system.user.entity.User;
import com.hsf302_group2.club_management_system.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberFormService {
    MemberFormRepository memberFormRepository;
    MemberFormMapper memberFormMapper;
    PreMemberService preMemberService;
    ClubMemberRepository clubMemberRepository;
    UserRepository userRepository;
    MailService mailService;

    public MemberFormResponse createMemberForm(MemberFormCreationRequest request){
        MemberForm memberForm = memberFormMapper.toMemberForm(request);
        PreMember preMember = preMemberService.getPreMemberResponseByToken();
        memberForm.setPreMember(preMember);

        List<MemberForm> existedForms = memberFormRepository.findByPreMember(preMember);

        boolean isPendingOrApproved = existedForms.stream().anyMatch(form -> form.getStatus() == FormStatus.PENDING || form.getStatus() == FormStatus.APPROVED);
        if (isPendingOrApproved){
            throw new AppException(ErrorCode.MEMBER_FORM_EXISTED);

        }

        memberForm.setStatus(FormStatus.PENDING);
        return memberFormMapper.toMemberFormResponse(memberFormRepository.save(memberForm));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<MemberFormResponse> getAllMemberForms() {
        return memberFormRepository.findAll().stream()
                .map(memberFormMapper::toMemberFormResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public MemberFormResponse getMemberFormById(int memberFormId) {
        return memberFormMapper.toMemberFormResponse(memberFormRepository.findById(memberFormId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_FORM_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public MemberFormResponse updateMemberFormStatus(int memberFormId, MemberFormReviewRequest request) {
        MemberForm memberForm = memberFormRepository.findById(memberFormId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_FORM_NOT_EXISTED));

        if (memberForm.getStatus() != FormStatus.PENDING) {
            throw new AppException(ErrorCode.MEMBER_FORM_REVIEWED);
        }

        memberForm.setStatus(request.getStatus());

        if (request.getStatus() == FormStatus.APPROVED) {
            PreMember preMember = memberForm.getPreMember();
            User user = preMember.getUser();
            user.setRole(Role.CLUB_MEMBER.name());
            userRepository.save(user);

            ClubMember clubMember = new ClubMember();
            clubMember.setPreMember(preMember);
            clubMember.setJoinedAt(LocalDateTime.now());
            clubMemberRepository.save(clubMember);
            mailService.sendWelcomeClubMemberEmail(clubMember.getPreMember().getUser().getEmail(), clubMember);

        }

        return memberFormMapper.toMemberFormResponse(memberFormRepository.save(memberForm));
    }


}
