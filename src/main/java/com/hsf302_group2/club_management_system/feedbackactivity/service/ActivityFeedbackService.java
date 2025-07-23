package com.hsf302_group2.club_management_system.feedbackactivity.service;

import com.hsf302_group2.club_management_system.activityregistration.entity.ActivityRegistration;
import com.hsf302_group2.club_management_system.activityregistration.repository.ActivityRegistrationRepository;
import com.hsf302_group2.club_management_system.common.enums.RegistrationStatus;
import com.hsf302_group2.club_management_system.common.exception.AppException;
import com.hsf302_group2.club_management_system.common.exception.ErrorCode;
import com.hsf302_group2.club_management_system.common.mapper.ActivityFeedbackMapper;
import com.hsf302_group2.club_management_system.feedbackactivity.dto.request.ActivityFeedbackCreationRequest;
import com.hsf302_group2.club_management_system.feedbackactivity.dto.response.ActivityFeedbackResponse;
import com.hsf302_group2.club_management_system.feedbackactivity.entity.ActivityFeedback;
import com.hsf302_group2.club_management_system.feedbackactivity.repository.ActivityFeedbackRepository;
import com.hsf302_group2.club_management_system.feedbackevent.dto.request.EventFeedbackCreationRequest;
import com.hsf302_group2.club_management_system.feedbackevent.dto.response.FeedbackFormActivityResponse;
import com.hsf302_group2.club_management_system.feedbackevent.entity.EventFeedback;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ActivityFeedbackService {
    ActivityFeedbackRepository activityFeedbackRepository;
    ActivityFeedbackMapper activityFeedbackMapper;
    ActivityRegistrationRepository activityRegistrationRepository;

    //    @PreAuthorize("hasRole('CLUB_MEMBER')")
    public ActivityFeedbackResponse submitActivityFeedback(ActivityFeedbackCreationRequest request, int activityRegistrationId){
        ActivityRegistration activityRegistration = activityRegistrationRepository.findById(activityRegistrationId)
                .orElseThrow(() -> new AppException(ErrorCode.NO_REGISTERED_ACTIVITIES));

        if (activityFeedbackRepository.existsFeedbackFormByActivityRegistration(activityRegistration)){
            throw new AppException(ErrorCode.FEEDBACK_ALREADY_SUBMITTED);
        }

        LocalDateTime activityEndDate = activityRegistration.getClubActivity().getEndDate();
        if (LocalDateTime.now().isBefore(activityEndDate)){
            throw new AppException(ErrorCode.ACTIVITY_NOT_ENDED_YET);
        }

        if (activityRegistration.getStatus() == RegistrationStatus.ABSENT){
            throw new AppException(ErrorCode.CANNOT_FEEDBACK_WHEN_ABSENT);
        }

        ActivityFeedback activityFeedback = activityFeedbackMapper.toActivityFeedback(request);
        activityFeedback.setActivityRegistration(activityRegistration);
        activityFeedback.setSubmittedAt(LocalDateTime.now());
        return activityFeedbackMapper.toActivityFeedbackResponse(activityFeedbackRepository.save(activityFeedback));

    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ActivityFeedbackResponse> getFeedbackFormByClubActivity(int clubActivityId){
        return activityFeedbackRepository.findFeedbackFormByClubActivity(clubActivityId);
    }

}
