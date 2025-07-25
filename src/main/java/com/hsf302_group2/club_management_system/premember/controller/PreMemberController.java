package com.hsf302_group2.club_management_system.premember.controller;

import com.hsf302_group2.club_management_system.common.dto.ApiResponse;
import com.hsf302_group2.club_management_system.memberform.dto.response.MemberFormResponse;
import com.hsf302_group2.club_management_system.premember.dto.response.PreMemberResponse;
import com.hsf302_group2.club_management_system.premember.service.PreMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/pre-members")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Pre Member API", description = "Quản lý pre member")
public class PreMemberController {
    PreMemberService preMemberService;

    @Operation(summary = "Lấy danh sách pre members")
    @GetMapping
    public ApiResponse<List<PreMemberResponse>> getAllPreMembers() {
        return ApiResponse.<List<PreMemberResponse>>builder()
                .success(true)
                .data(preMemberService.getAllPreMembers())
                .build();
    }


}
