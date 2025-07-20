package com.hsf302_group2.club_management_system.common.mapper;

import com.hsf302_group2.club_management_system.clubmember.dto.response.ClubMemberResponse;
import com.hsf302_group2.club_management_system.clubmember.entity.ClubMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClubMemberMapper {
    @Mapping(source = "id", target = "clubMemberId")
    @Mapping(target = "userId", expression = "java(clubMember.getPreMember().getUser().getId())")
    @Mapping(target = "email", expression = "java(clubMember.getPreMember().getUser().getEmail())")
    @Mapping(target = "fullName", expression = "java(clubMember.getPreMember().getUser().getFullName())")
    @Mapping(target = "userStatus", expression = "java(clubMember.getPreMember().getUser().getStatus())")
    @Mapping(source = "preMember.gender", target = "gender")
    @Mapping(source = "preMember.address", target = "address")
    @Mapping(source = "preMember.phoneNumber", target = "phoneNumber")
    @Mapping(source = "preMember.dob", target = "dob")
    ClubMemberResponse toClubMemberResponse(ClubMember clubMember);
}
