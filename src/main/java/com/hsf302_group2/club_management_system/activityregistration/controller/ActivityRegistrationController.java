package com.hsf302_group2.club_management_system.activityregistration.controller;

import com.hsf302_group2.club_management_system.activityregistration.dto.request.ActivityRegistrationAttendRequest;
import com.hsf302_group2.club_management_system.activityregistration.dto.response.ActivityRegistrationResponse;
import com.hsf302_group2.club_management_system.activityregistration.dto.response.MyActivityRegistrationResponse;
import com.hsf302_group2.club_management_system.activityregistration.entity.ActivityRegistration;
import com.hsf302_group2.club_management_system.activityregistration.service.ActivityRegistrationService;
import com.hsf302_group2.club_management_system.clubactivity.dto.response.ClubActivityResponse;
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
@RequestMapping("api/club-activity-registrations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Activity Registration API", description = "Quản lý đăng ký hoạt động câu lạc bộ")
public class ActivityRegistrationController {
    ActivityRegistrationService  activityRegistrationService;

    @PostMapping("/register/{clubActivityId}")
    public ApiResponse<Void> registerForClubActivity(@PathVariable int clubActivityId) {
        activityRegistrationService.activityRegistration(clubActivityId);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Đăng ký tham gia hoạt động thành công !")
                .build();
    }

    @PutMapping("/check-attendance/{clubActivityId}/{clubMemberId}")
    public ApiResponse<Void> checkAttendanceClubActivity(@PathVariable("clubActivityId") int clubActivityId,
                                                         @PathVariable("clubMemberId") String clubMemberId,
                                                         @RequestBody @Valid ActivityRegistrationAttendRequest request) {
        activityRegistrationService.checkAttendance(clubActivityId, clubMemberId, request);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Điểm danh thành công !")
                .build();
    }

    @GetMapping("/{clubActivityId}")
    public ApiResponse<List<ActivityRegistrationResponse>> getActivityRegistration(@PathVariable("clubActivityId") int clubActivityId) {
        return ApiResponse.<List<ActivityRegistrationResponse>>builder()
                .success(true)
                .data(activityRegistrationService.getActivityRegistrationByClubActivityId(clubActivityId))
                .build();
    }

    @Operation(summary = "Lấy danh sách hoạt động clb đã đăng ký")
    @GetMapping("/me/registered-activities")
    public ApiResponse<List<MyActivityRegistrationResponse>> getRegisteredActivities() {
        return ApiResponse.<List<MyActivityRegistrationResponse>>builder()
                .success(true)
                .data(activityRegistrationService.getRegisteredActivities())
                .build();
    }




}
