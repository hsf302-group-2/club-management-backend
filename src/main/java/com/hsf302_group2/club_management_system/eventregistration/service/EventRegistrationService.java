package com.hsf302_group2.club_management_system.eventregistration.service;

import com.hsf302_group2.club_management_system.clubevent.entity.ClubEvent;
import com.hsf302_group2.club_management_system.clubevent.repository.ClubEventRepository;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.common.mapper.EventRegistrationMapper;
import com.hsf302_group2.club_management_system.eventregistration.dto.response.EventRegistrationResponse;
import com.hsf302_group2.club_management_system.eventregistration.entity.EventRegistration;
import com.hsf302_group2.club_management_system.eventregistration.repository.EventRegistrationRepository;
import com.hsf302_group2.club_management_system.mail.MailService;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import com.hsf302_group2.club_management_system.premember.service.PreMemberService;
import com.hsf302_group2.club_management_system.user.entity.User;
import com.hsf302_group2.club_management_system.user.service.UserService;
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
public class EventRegistrationService {
    EventRegistrationRepository eventRegistrationRepository;
    MailService mailService;
    PreMemberService preMemberService;
    ClubEventRepository clubEventRepository;

    @PreAuthorize("hasRole('PRE_MEMBER')")
    public void registerForEvent(int eventClubId) {
        PreMember preMember = preMemberService.getPreMemberResponseByToken();
        ClubEvent clubEvent = clubEventRepository.findById(eventClubId)
                .orElseThrow(() -> new AppException(ErrorCode.CLUB_EVENT_NOT_EXISTED));

        boolean isRegistered = eventRegistrationRepository.existsByClubEventIdAndPreMemberId(eventClubId, preMember.getId());
        if (isRegistered) {
            throw new AppException(ErrorCode.CLUB_EVENT_ALREADY_REGISTERED);
        }

        EventRegistration eventRegistration = new EventRegistration();
        eventRegistration.setRegistrationTime(LocalDateTime.now());
        if (eventRegistration.getRegistrationTime().isAfter(clubEvent.getRegistrationDeadline())){
            throw new AppException(ErrorCode.REGISTRATION_TIME_PASSED);
        }

        eventRegistration.setClubEvent(clubEvent);
        eventRegistration.setPreMember(preMember);

        eventRegistrationRepository.save(eventRegistration);
        mailService.sendRegistrationClubEventEmail(preMember.getUser().getEmail(),clubEvent);

    }



}
