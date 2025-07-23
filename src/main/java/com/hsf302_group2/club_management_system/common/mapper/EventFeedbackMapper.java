package com.hsf302_group2.club_management_system.common.mapper;

import com.hsf302_group2.club_management_system.feedbackevent.dto.request.EventFeedbackCreationRequest;
import com.hsf302_group2.club_management_system.feedbackevent.dto.response.EventFeedbackResponse;
import com.hsf302_group2.club_management_system.feedbackevent.dto.response.FeedbackFormActivityResponse;
import com.hsf302_group2.club_management_system.feedbackevent.entity.EventFeedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventFeedbackMapper {
    @Mapping(source = "eventRegistration.clubEvent.id", target = "clubEventId")
    @Mapping(source = "eventRegistration.preMember.id", target = "preMemberId")
    @Mapping(source = "eventRegistration.id", target = "eventRegistrationId")
    @Mapping(source = "eventRegistration.preMember.user.fullName", target = "fullName")
    EventFeedbackResponse toEventFeedbackResponse(EventFeedback eventFeedback);
    EventFeedback toEventFeedback(EventFeedbackCreationRequest eventFeedbackCreationRequest);

}
