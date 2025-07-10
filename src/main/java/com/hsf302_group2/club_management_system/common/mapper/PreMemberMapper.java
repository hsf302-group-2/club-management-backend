package com.hsf302_group2.club_management_system.common.mapper;

import com.hsf302_group2.club_management_system.premember.dto.request.PreMemberUpdateRequest;
import com.hsf302_group2.club_management_system.premember.dto.response.PreMemberResponse;
import com.hsf302_group2.club_management_system.premember.entity.PreMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PreMemberMapper {
    @Mapping(source = "id", target = "preMemberId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.status", target = "userStatus")
    PreMemberResponse toPreMemberResponse(PreMember preMember);

    void updatePreMember(@MappingTarget PreMember preMember, PreMemberUpdateRequest request);
    PreMember toPreMember(PreMemberResponse preMemberResponse);

}
