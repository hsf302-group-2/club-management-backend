package com.hsf302_group2.club_management_system.eventregistration.controller;

import com.hsf302_group2.club_management_system.common.dto.ApiResponse;
import com.hsf302_group2.club_management_system.eventregistration.dto.response.EventRegistrationResponse;
import com.hsf302_group2.club_management_system.eventregistration.service.EventRegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/event-registrations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Event Registration API", description = "Quản lý đăng ký sự kiện")
public class EventRegistrationController {
    EventRegistrationService eventRegistrationService;

    @PostMapping("/register/{eventClubId}")
    public ApiResponse<Void> registerForEvent(@PathVariable int eventClubId) {
        eventRegistrationService.registerForEvent(eventClubId);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Đăng ký sự kiện thành công !")
                .build();
    }

}
