package com.hsf302_group2.club_management_system.clubmember.controller;

import com.hsf302_group2.club_management_system.clubmember.dto.response.ClubMemberResponse;
import com.hsf302_group2.club_management_system.clubmember.service.ClubMemberService;
import com.hsf302_group2.club_management_system.common.dto.ApiResponse;
import com.hsf302_group2.club_management_system.memberform.dto.response.MemberFormResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/club-members")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Club Member API", description = "Quản lý thành viên câu lạc bộ")
public class ClubMemberController {
    ClubMemberService clubMemberService;

    @Operation(summary = "Lấy danh sách club members")
    @GetMapping
    public ApiResponse<List<ClubMemberResponse>> getAllClubMembers() {
        return ApiResponse.<List<ClubMemberResponse>>builder()
                .success(true)
                .data(clubMemberService.getAllClubMembers())
                .build();
    }
}
