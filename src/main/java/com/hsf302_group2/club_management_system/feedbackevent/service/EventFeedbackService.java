package com.hsf302_group2.club_management_system.feedbackevent.service;

import com.hsf302_group2.club_management_system.activityregistration.entity.ActivityRegistration;
import com.hsf302_group2.club_management_system.activityregistration.repository.ActivityRegistrationRepository;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.common.mapper.EventFeedbackMapper;
import com.hsf302_group2.club_management_system.eventregistration.entity.EventRegistration;
import com.hsf302_group2.club_management_system.eventregistration.repository.EventRegistrationRepository;
import com.hsf302_group2.club_management_system.feedbackevent.dto.request.EventFeedbackCreationRequest;
import com.hsf302_group2.club_management_system.feedbackevent.dto.response.EventFeedbackResponse;
import com.hsf302_group2.club_management_system.feedbackevent.dto.response.FeedbackFormActivityResponse;
import com.hsf302_group2.club_management_system.feedbackevent.entity.EventFeedback;
import com.hsf302_group2.club_management_system.feedbackevent.repository.EventFeedbackRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class EventFeedbackService {
    EventFeedbackRepository eventFeedbackRepository;
    EventFeedbackMapper eventFeedbackMapper;
    EventRegistrationRepository eventRegistrationRepository;

//    @PreAuthorize("hasRole('PRE_MEMBER')")
    public EventFeedbackResponse submitEventFeedback(EventFeedbackCreationRequest request, int eventRegistrationId){
        EventRegistration eventRegistration = eventRegistrationRepository.findById(eventRegistrationId)
                .orElseThrow(() -> new AppException(ErrorCode.NO_REGISTERED_EVENTS));

        if (eventFeedbackRepository.existsFeedbackFormByEventRegistration(eventRegistration)){
            throw new AppException(ErrorCode.FEEDBACK_ALREADY_SUBMITTED);
        }

        LocalDate eventDate = eventRegistration.getClubEvent().getEventDate();
        LocalTime endTime = eventRegistration.getClubEvent().getEndTime();
        LocalDateTime eventEndDate = LocalDateTime.of(eventDate, endTime);
        if (LocalDateTime.now().isBefore(eventEndDate)){
            throw new AppException(ErrorCode.EVENT_NOT_ENDED_YET);
        }

        EventFeedback eventFeedback = eventFeedbackMapper.toEventFeedback(request);
        eventFeedback.setEventRegistration(eventRegistration);
        eventFeedback.setSubmittedAt(LocalDateTime.now());
        return eventFeedbackMapper.toEventFeedbackResponse(eventFeedbackRepository.save(eventFeedback));
    }


    @PreAuthorize("hasRole('ADMIN')")
    public List<EventFeedbackResponse> getFeedbackFormByClubEvent(int clubEventId){
        return eventFeedbackRepository.findFeedbackFormByClubEvent(clubEventId);
    }
}
