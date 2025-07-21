package com.hsf302_group2.club_management_system.clubactivity.controller;

import com.hsf302_group2.club_management_system.clubactivity.dto.request.ClubActivityCreationRequest;
import com.hsf302_group2.club_management_system.clubactivity.dto.request.ClubActivityUpdateRequest;
import com.hsf302_group2.club_management_system.clubactivity.dto.response.ClubActivityResponse;
import com.hsf302_group2.club_management_system.clubactivity.service.ClubActivityService;
import com.hsf302_group2.club_management_system.clubevent.dto.request.ClubEventCreationRequest;
import com.hsf302_group2.club_management_system.clubevent.dto.request.ClubEventUpdateRequest;
import com.hsf302_group2.club_management_system.clubevent.dto.response.ClubEventResponse;
import com.hsf302_group2.club_management_system.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/club-activities")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Club Activity API", description = "Quản lý thông tin hoạt động câu lạc bộ")
public class ClubActivityController {
    ClubActivityService  clubActivityService;

    @PostMapping
    @Operation(summary = "Tạo mới hoạt động câu lạc bộ")
    public ApiResponse<ClubActivityResponse> createClubActivity(@RequestBody @Valid ClubActivityCreationRequest request) {
        return ApiResponse.<ClubActivityResponse>builder()
                .success(true)
                .data(clubActivityService.createClubActivity(request))
                .build();
    }

    @Operation(summary = "Lấy danh sách hoạt động câu lạc bộ")
    @GetMapping
    public ApiResponse<List<ClubActivityResponse>> getAllClubActivities() {
        return ApiResponse.<List<ClubActivityResponse>>builder()
                .success(true)
                .data(clubActivityService.getAllClubActivities())
                .build();
    }

    @Operation(summary = "Lấy hoạt động câu lạc bộ theo ID")
    @GetMapping("/{clubActivityId}")
    public ApiResponse<ClubActivityResponse> getClubActivity(@PathVariable int clubActivityId) {
        return ApiResponse.<ClubActivityResponse>builder()
                .data(clubActivityService.getClubActivity(clubActivityId))
                .success(true)
                .build();
    }

    @Operation(summary = "Cập nhật thông tin hoạt động câu lạc bộ")
    @PutMapping("/{clubActivityId}")
    public ApiResponse<ClubActivityResponse> updateClubEvent (@PathVariable int clubActivityId, @RequestBody ClubActivityUpdateRequest request){
        return ApiResponse.<ClubActivityResponse>builder()
                .data(clubActivityService.updateClubActivity(clubActivityId, request))
                .success(true)
                .build();
    }

    @Operation(summary = "Lấy danh sách hoạt động clb đã đăng ký")
    @GetMapping("/me/registered-activities")
    public ApiResponse<List<ClubActivityResponse>> getRegisteredActivities() {
        return ApiResponse.<List<ClubActivityResponse>>builder()
                .success(true)
                .data(clubActivityService.getRegisteredActivities())
                .build();
    }


}
