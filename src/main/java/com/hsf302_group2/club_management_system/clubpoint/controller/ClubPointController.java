package com.hsf302_group2.club_management_system.clubpoint.controller;

import com.hsf302_group2.club_management_system.clubactivity.dto.response.ClubActivityResponse;
import com.hsf302_group2.club_management_system.clubpoint.dto.response.ClubPointHistoryResponse;
import com.hsf302_group2.club_management_system.clubpoint.dto.response.ClubPointTotalResponse;
import com.hsf302_group2.club_management_system.clubpoint.service.ClubPointService;
import com.hsf302_group2.club_management_system.common.dto.ApiResponse;
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
@RequestMapping("/api/club-points")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Club Point API", description = "Quản lý điểm thi đua câu lạc bộ")
public class ClubPointController {
    ClubPointService clubPointService;

    @Operation(summary = "Lấy danh sách club member và điểm")
    @GetMapping("/all-club-member/point")
    public ApiResponse<List<ClubPointTotalResponse>> getAllClubMemberPoints() {
        return ApiResponse.<List<ClubPointTotalResponse>>builder()
                .success(true)
                .data(clubPointService.getAllClubMemberPoints())
                .build();
    }

    @Operation(summary = "Lấy top 3 club member điểm cao nhất")
    @GetMapping("/top/point")
    public ApiResponse<List<ClubPointTotalResponse>> getTopClubMembers() {
        return ApiResponse.<List<ClubPointTotalResponse>>builder()
                .success(true)
                .data(clubPointService.getTopClubMemberPoints())
                .build();
    }

    @Operation(summary = "Lấy điểm của tôi")
    @GetMapping("/me/point")
    public ApiResponse<ClubPointTotalResponse> getMyTotalPoint() {
        return ApiResponse.<ClubPointTotalResponse>builder()
                .success(true)
                .data(clubPointService.getClubMemberPointById())
                .build();
    }

    @Operation(summary = "Lấy danh sách lịch sử cộng / trừ điểm")
    @GetMapping("/history/point")
    public ApiResponse<List<ClubPointHistoryResponse>> getMyHistoryPoint() {
        return ApiResponse.<List<ClubPointHistoryResponse>>builder()
                .success(true)
                .data(clubPointService.getHistoryPoint())
                .build();
    }




}
