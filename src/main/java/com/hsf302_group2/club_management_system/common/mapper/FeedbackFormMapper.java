package com.hsf302_group2.club_management_system.common.mapper;

import com.hsf302_group2.club_management_system.feedbackform.dto.response.FeedbackFormResponse;
import com.hsf302_group2.club_management_system.feedbackform.entity.FeedbackForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FeedbackFormMapper {
    @Mapping(source = "clubEvent.id", target = "clubEventId")
    @Mapping(source = "preMember.id", target = "preMemberId")
    FeedbackFormResponse toFeedbackFormResponse(FeedbackForm feedbackForm);
}
