package com.hsf302_group2.club_management_system.common.mapper;

import com.hsf302_group2.club_management_system.clubevent.dto.request.ClubEventCreationRequest;
import com.hsf302_group2.club_management_system.clubevent.dto.request.ClubEventUpdateRequest;
import com.hsf302_group2.club_management_system.clubevent.dto.response.ClubEventResponse;
import com.hsf302_group2.club_management_system.clubevent.entity.ClubEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClubEventMapper {
    @Mapping(source = "id", target = "clubEventId")
    @Mapping(expression = "java(clubEvent.getEventRegistrations() != null ? clubEvent.getEventRegistrations().size() : 0)", target = "currentParticipants")
    ClubEventResponse toClubEventResponse(ClubEvent clubEvent);
    ClubEvent toClubEvent(ClubEventCreationRequest clubEventCreationRequest);
    void updateClubEvent(@MappingTarget ClubEvent clubEvent, ClubEventUpdateRequest clubEventUpdateRequest);
}
