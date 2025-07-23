package com.hsf302_group2.club_management_system.common.mapper;

import com.hsf302_group2.club_management_system.feedbackactivity.dto.request.ActivityFeedbackCreationRequest;
import com.hsf302_group2.club_management_system.feedbackactivity.dto.response.ActivityFeedbackResponse;
import com.hsf302_group2.club_management_system.feedbackactivity.entity.ActivityFeedback;
import com.hsf302_group2.club_management_system.feedbackevent.dto.request.EventFeedbackCreationRequest;
import com.hsf302_group2.club_management_system.feedbackevent.dto.response.FeedbackFormActivityResponse;
import com.hsf302_group2.club_management_system.feedbackevent.entity.EventFeedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ActivityFeedbackMapper {
    @Mapping(source = "activityRegistration.clubActivity.id", target = "clubActivityId")
    @Mapping(source = "activityRegistration.clubMember.id", target = "clubMemberId")
    @Mapping(source = "activityRegistration.id", target = "activityRegistrationId")
    @Mapping(source = "activityRegistration.clubMember.preMember.user.fullName", target = "fullName")
    ActivityFeedbackResponse toActivityFeedbackResponse(ActivityFeedback activityFeedback);
    ActivityFeedback toActivityFeedback(ActivityFeedbackCreationRequest activityFeedbackCreationRequest);
}
