package com.hsf302_group2.club_management_system.activityregistration.service;

import com.hsf302_group2.club_management_system.activityregistration.dto.request.ActivityRegistrationAttendRequest;
import com.hsf302_group2.club_management_system.activityregistration.dto.response.ActivityRegistrationResponse;
import com.hsf302_group2.club_management_system.activityregistration.dto.response.MyActivityRegistrationResponse;
import com.hsf302_group2.club_management_system.activityregistration.entity.ActivityRegistration;
import com.hsf302_group2.club_management_system.activityregistration.repository.ActivityRegistrationRepository;
import com.hsf302_group2.club_management_system.clubactivity.dto.response.ClubActivityResponse;
import com.hsf302_group2.club_management_system.clubactivity.entity.ClubActivity;
import com.hsf302_group2.club_management_system.clubactivity.repository.ClubActivityRepository;
import com.hsf302_group2.club_management_system.clubmember.entity.ClubMember;
import com.hsf302_group2.club_management_system.clubpoint.entity.ClubPoint;
import com.hsf302_group2.club_management_system.clubpoint.repository.ClubPointRepository;
import com.hsf302_group2.club_management_system.common.enums.RegistrationStatus;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.common.mapper.ActivityRegistrationMapper;
import com.hsf302_group2.club_management_system.mail.MailService;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import com.hsf302_group2.club_management_system.premember.service.PreMemberService;
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
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ActivityRegistrationService {

    MailService mailService;
    ClubActivityRepository clubActivityRepository;
    PreMemberService preMemberService;
    ActivityRegistrationRepository activityRegistrationRepository;
    ActivityRegistrationMapper activityRegistrationMapper;
    ClubPointRepository clubPointRepository;

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

    @PreAuthorize("hasRole('ADMIN')")
    public void checkAttendance(int clubActivityId, String clubMemberId, ActivityRegistrationAttendRequest request){
        ActivityRegistration registration = activityRegistrationRepository.findActivityRegistrationByClubActivityIdAndClubMemberId(clubActivityId, clubMemberId)
                .orElseThrow(() -> new AppException(ErrorCode.NO_REGISTERED_ACTIVITIES));

        registration.setStatus(request.getStatus());
        activityRegistrationRepository.save(registration);

        ClubMember clubMember = registration.getClubMember();
        if (request.getStatus() == RegistrationStatus.ATTENDED){
            clubPointRepository.save(new ClubPoint(clubMember, 10, "Đã tham gia hoạt động CLB", LocalDateTime.now()));
        }
        else if (request.getStatus() == RegistrationStatus.ABSENT){
            clubPointRepository.save(new ClubPoint(clubMember, -15, "Vắng mặt hoạt động CLB", LocalDateTime.now()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ActivityRegistrationResponse> getActivityRegistrationByClubActivityId(int clubActivityId) {
        return activityRegistrationRepository.findActivityRegistrationByClubActivityId(clubActivityId);
    }

    @PreAuthorize("hasRole('CLUB_MEMBER')")
    public List<MyActivityRegistrationResponse> getRegisteredActivities(){
        PreMember preMember = preMemberService.getPreMemberResponseByToken();
        List<ActivityRegistration> activities = activityRegistrationRepository.findByUserActivityRegistrations(preMember.getClubMember().getId());

        if (activities.isEmpty()){
            throw new AppException(ErrorCode.NO_REGISTERED_ACTIVITIES);
        }

        return activities.stream()
                .map(activityRegistrationMapper::toMyActivityRegistrationResponse)
                .collect(Collectors.toList());
    }



}
