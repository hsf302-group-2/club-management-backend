package com.hsf302_group2.club_management_system.common.mapper;

import com.hsf302_group2.club_management_system.eventregistration.dto.response.EventRegistrationResponse;
import com.hsf302_group2.club_management_system.eventregistration.entity.EventRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventRegistrationMapper {
    @Mapping(source = "clubEvent.id", target = "clubEventId")
    @Mapping(target = "preMemberId", expression = "java(eventRegistration.getPreMember().getId())")
    @Mapping(source = "clubEvent.title", target = "title")
    @Mapping(source = "clubEvent.snippet", target = "snippet")
    @Mapping(source = "clubEvent.description", target = "description")
    @Mapping(source = "clubEvent.location", target = "location")
    @Mapping(source = "clubEvent.speaker", target = "speaker")
    @Mapping(source = "clubEvent.eventDate", target = "eventDate")
    @Mapping(source = "clubEvent.startTime", target = "startTime")
    @Mapping(source = "clubEvent.endTime", target = "endTime")
    EventRegistrationResponse toEventRegistrationResponse(EventRegistration eventRegistration);
}
