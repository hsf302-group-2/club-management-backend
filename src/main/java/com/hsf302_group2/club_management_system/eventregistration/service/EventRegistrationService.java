package com.hsf302_group2.club_management_system.eventregistration.service;

import com.hsf302_group2.club_management_system.clubevent.entity.ClubEvent;
import com.hsf302_group2.club_management_system.clubevent.repository.ClubEventRepository;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.eventregistration.entity.EventRegistration;
import com.hsf302_group2.club_management_system.eventregistration.repository.EventRegistrationRepository;
import com.hsf302_group2.club_management_system.mail.MailService;
import com.hsf302_group2.club_management_system.user.entity.User;
import com.hsf302_group2.club_management_system.user.service.UserService;
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
public class EventRegistrationService {
    EventRegistrationRepository eventRegistrationRepository;
    MailService mailService;
    UserService userService;
    ClubEventRepository clubEventRepository;

    @PreAuthorize("hasRole('PRE_MEMBER') or hasRole('CLUB_MEMBER')")
    public void registerForEvent(int eventClubId) {
        User user = userService.getUserResponseByToken();
        ClubEvent clubEvent = clubEventRepository.findById(eventClubId)
                .orElseThrow(() -> new AppException(ErrorCode.CLUB_EVENT_NOT_EXISTED));

        boolean isRegistered = eventRegistrationRepository.existsByClubEventIdAndUserId(eventClubId, user.getId());
        if (isRegistered) {
            throw new AppException(ErrorCode.CLUB_EVENT_ALREADY_REGISTERED);
        }

        EventRegistration eventRegistration = new EventRegistration();
        eventRegistration.setClubEvent(clubEvent);
        eventRegistration.setUser(user);
        eventRegistration.setRegistrationTime(LocalDateTime.now());

        eventRegistrationRepository.save(eventRegistration);
        mailService.sendRegistrationClubEventEmail(user.getEmail(),clubEvent);

    }
}
