package com.hsf302_group2.club_management_system.common.mapper;

import com.hsf302_group2.club_management_system.feedbackform.dto.response.FeedbackFormActivityResponse;
import com.hsf302_group2.club_management_system.feedbackform.dto.response.FeedbackFormEventResponse;
import com.hsf302_group2.club_management_system.feedbackform.entity.FeedbackForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FeedbackFormMapper {
    @Mapping(source = "eventRegistration.clubEvent.id", target = "clubEventId")
    @Mapping(source = "eventRegistration.preMember.id", target = "preMemberId")
    @Mapping(source = "eventRegistration.id", target = "eventRegistrationId")
    @Mapping(source = "eventRegistration.preMember.user.fullName", target = "fullName")
    FeedbackFormEventResponse toFeedbackFormEventResponse(FeedbackForm feedbackForm);

    @Mapping(source = "activityRegistration.clubActivity.id", target = "clubActivityId")
    @Mapping(source = "activityRegistration.clubMember.id", target = "clubMemberId")
    @Mapping(source = "activityRegistration.id", target = "activityRegistrationId")
    @Mapping(source = "activityRegistration.clubMember.preMember.user.fullName", target = "fullName")
    FeedbackFormActivityResponse toFeedbackFormActivityResponse(FeedbackForm feedbackForm);
}
