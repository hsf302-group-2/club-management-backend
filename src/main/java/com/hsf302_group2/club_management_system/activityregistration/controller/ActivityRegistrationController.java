package com.hsf302_group2.club_management_system.activityregistration.controller;

import com.hsf302_group2.club_management_system.activityregistration.service.ActivityRegistrationService;
import com.hsf302_group2.club_management_system.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
