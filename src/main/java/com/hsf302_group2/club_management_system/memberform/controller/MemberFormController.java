package com.hsf302_group2.club_management_system.memberform.controller;

import com.hsf302_group2.club_management_system.common.dto.ApiResponse;
import com.hsf302_group2.club_management_system.memberform.dto.request.MemberFormCreationRequest;
import com.hsf302_group2.club_management_system.memberform.dto.request.MemberFormReviewRequest;
import com.hsf302_group2.club_management_system.memberform.dto.response.MemberFormResponse;
import com.hsf302_group2.club_management_system.memberform.service.MemberFormService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/member-forms")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Member Form API", description = "Quản lý member form")
public class MemberFormController {
    MemberFormService memberFormService;

    @PostMapping
    @Operation(summary = "Tạo member form")
    public ApiResponse<MemberFormResponse> createMemberForm(@RequestBody @Valid MemberFormCreationRequest request) {
        return ApiResponse.<MemberFormResponse>builder()
                .success(true)
                .data(memberFormService.createMemberForm(request))
                .build();
    }

    @Operation(summary = "Lấy danh sách member form")
    @GetMapping
    public ApiResponse<List<MemberFormResponse>> getAllMemberForms() {
        return ApiResponse.<List<MemberFormResponse>>builder()
                .success(true)
                .data(memberFormService.getAllMemberForms())
                .build();
    }

    @Operation(summary = "Lấy member form theo ID")
    @GetMapping("/{memberFormId}")
    public ApiResponse<MemberFormResponse> getMedication(@PathVariable int memberFormId) {
        return ApiResponse.<MemberFormResponse>builder()
                .data(memberFormService.getMemberFormById(memberFormId))
                .success(true)
                .build();
    }

    @Operation(summary = "Update status member form")
    @PutMapping("/{memberFormId}/review")
    public ApiResponse<MemberFormResponse> reviewMemberForm(@PathVariable int memberFormId, @RequestBody @Valid MemberFormReviewRequest request) {
        return ApiResponse.<MemberFormResponse>builder()
                .data(memberFormService.updateMemberFormStatus(memberFormId, request))
                .success(true)
                .build();
    }

}
