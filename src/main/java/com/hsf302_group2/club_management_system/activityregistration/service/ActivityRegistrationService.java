package com.hsf302_group2.club_management_system.activityregistration.service;

import com.hsf302_group2.club_management_system.activityregistration.entity.ActivityRegistration;
import com.hsf302_group2.club_management_system.activityregistration.repository.ActivityRegistrationRepository;
import com.hsf302_group2.club_management_system.clubactivity.entity.ClubActivity;
import com.hsf302_group2.club_management_system.clubactivity.repository.ClubActivityRepository;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.mail.MailService;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import com.hsf302_group2.club_management_system.premember.service.PreMemberService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ActivityRegistrationService {

    MailService mailService;
    ClubActivityRepository clubActivityRepository;
    PreMemberService preMemberService;
    ActivityRegistrationRepository activityRegistrationRepository;

    @PreAuthorize("hasRole('CLUB_MEMBER')")
    public void activityRegistration(int clubActivityId) {
        PreMember preMember = preMemberService.getPreMemberResponseByToken();
        ClubActivity clubActivity = clubActivityRepository.findById(clubActivityId)
                .orElseThrow(() -> new AppException(ErrorCode.CLUB_ACTIVITY_NOT_EXISTED));

        boolean isRegistered = activityRegistrationRepository.existsByClubActivityIdAndClubMemberId(clubActivityId, preMember.getClubMember().getId());
        if (isRegistered){
            throw new AppException(ErrorCode.CLUB_ACTIVITY_ALREADY_REGISTERED);
        }

        ActivityRegistration activityRegistration = new ActivityRegistration();
        activityRegistration.setRegistrationTime(LocalDateTime.now());

        if (activityRegistration.getRegistrationTime().isAfter(clubActivity.getRegistrationDeadline())){
            throw new AppException(ErrorCode.REGISTRATION_TIME_PASSED);
        }
        activityRegistration.setClubActivity(clubActivity);
        activityRegistration.setClubMember(preMember.getClubMember());
        activityRegistrationRepository.save(activityRegistration);
        mailService.sendClubActivityRegistrationEmail(preMember.getUser().getEmail(), clubActivity);

    }
}
