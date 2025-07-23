package com.hsf302_group2.club_management_system.common.mapper;

import com.hsf302_group2.club_management_system.activityregistration.dto.response.ActivityRegistrationResponse;
import com.hsf302_group2.club_management_system.activityregistration.dto.response.MyActivityRegistrationResponse;
import com.hsf302_group2.club_management_system.activityregistration.entity.ActivityRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ActivityRegistrationMapper {
    @Mapping(source = "clubActivity.id", target = "clubActivityId")
    @Mapping(source = "clubMember.id", target = "clubMemberId")
    @Mapping(source = "clubMember.preMember.user.fullName", target = "fullName")
    @Mapping(source = "clubMember.preMember.gender", target = "gender")
    ActivityRegistrationResponse toActivityRegistrationResponse(ActivityRegistration activityRegistration);

    @Mapping(source = "clubActivity.id", target = "clubActivityId")
    @Mapping(source = "clubMember.id", target = "clubMemberId")
    @Mapping(source = "clubActivity.title", target = "title")
    @Mapping(source = "clubActivity.description", target = "description")
    @Mapping(source = "clubActivity.location", target = "location")
    @Mapping(source = "clubActivity.type", target = "type")
    @Mapping(source = "clubActivity.startDate", target = "startDate")
    @Mapping(source = "clubActivity.endDate", target = "endDate")
    MyActivityRegistrationResponse toMyActivityRegistrationResponse(ActivityRegistration activityRegistration);

}
