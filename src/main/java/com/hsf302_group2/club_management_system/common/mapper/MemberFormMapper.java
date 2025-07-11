package com.hsf302_group2.club_management_system.common.mapper;

import com.hsf302_group2.club_management_system.memberform.dto.request.MemberFormCreationRequest;
import com.hsf302_group2.club_management_system.memberform.dto.response.MemberFormResponse;
import com.hsf302_group2.club_management_system.memberform.entity.MemberForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MemberFormMapper {

    @Mapping(source = "preMember.id", target = "preMemberId")
    MemberFormResponse toMemberFormResponse(MemberForm memberForm
    );
    MemberForm toMemberForm(MemberFormCreationRequest memberFormCreationRequest);
}
