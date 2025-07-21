package com.hsf302_group2.club_management_system.common.mapper;

import com.hsf302_group2.club_management_system.clubactivity.dto.request.ClubActivityCreationRequest;
import com.hsf302_group2.club_management_system.clubactivity.dto.request.ClubActivityUpdateRequest;
import com.hsf302_group2.club_management_system.clubactivity.dto.response.ClubActivityResponse;
import com.hsf302_group2.club_management_system.clubactivity.entity.ClubActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClubActivityMapper {
    @Mapping(source = "id", target = "clubActivityId")
    @Mapping(expression = "java(clubActivity.getActivityRegistrations() != null ? clubActivity.getActivityRegistrations().size() : 0)", target = "currentParticipants")
    ClubActivityResponse toClubActivityResponse(ClubActivity clubActivity);
    ClubActivity toClubActivity(ClubActivityCreationRequest  clubActivityCreationRequest);
    void updateClubActivity(@MappingTarget ClubActivity clubActivity, ClubActivityUpdateRequest  clubActivityUpdateRequest);
}
