package com.hsf302_group2.club_management_system.premember.controller;

import com.hsf302_group2.club_management_system.clubevent.dto.request.ClubEventUpdateRequest;
import com.hsf302_group2.club_management_system.clubevent.dto.response.ClubEventResponse;
import com.hsf302_group2.club_management_system.common.dto.ApiResponse;
import com.hsf302_group2.club_management_system.memberform.dto.response.MemberFormResponse;
import com.hsf302_group2.club_management_system.premember.dto.request.PreMemberUpdatePasswordRequest;
import com.hsf302_group2.club_management_system.premember.dto.request.PreMemberUpdateRequest;
import com.hsf302_group2.club_management_system.premember.dto.response.PreMemberResponse;
import com.hsf302_group2.club_management_system.premember.service.PreMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Lấy profile pre members")
    @GetMapping("me/profile")
    public ApiResponse<PreMemberResponse> getPreMemberProfile() {
        return ApiResponse.<PreMemberResponse>builder()
                .success(true)
                .data(preMemberService.getPreMemberProfile())
                .build();
    }

    @Operation(summary = "Đổi mật khẩu Pre member ")
    @PutMapping("/me/change-password")
    public ApiResponse<Boolean> changePassword(@RequestBody @Valid PreMemberUpdatePasswordRequest request) {
        return ApiResponse.<Boolean>builder()
                .success(true)
                .data(preMemberService.changePassword(request))
                .build();
    }

    @Operation(summary = "Cập nhật thông tin premember")
    @PutMapping()
    public ApiResponse<PreMemberResponse> updatePreMemberProfile (@RequestBody PreMemberUpdateRequest request){
        return ApiResponse.<PreMemberResponse>builder()
                .data(preMemberService.updatePreMemberProfile(request))
                .success(true)
                .build();
    }


}
