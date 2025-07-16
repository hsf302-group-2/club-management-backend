package com.hsf302_group2.club_management_system.clubevent.controller;

import com.hsf302_group2.club_management_system.clubevent.dto.request.ClubEventCreationRequest;
import com.hsf302_group2.club_management_system.clubevent.dto.request.ClubEventUpdateRequest;
import com.hsf302_group2.club_management_system.clubevent.dto.response.ClubEventResponse;
import com.hsf302_group2.club_management_system.clubevent.service.ClubEventService;
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
@RequestMapping("/api/club-events")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Club Event API", description = "Quản lý thông tin sự kiện câu lạc bộ")
public class ClubEventController {
    ClubEventService clubEventService;

    @PostMapping
    @Operation(summary = "Tạo mới sự kiện câu lạc bộ")
    public ApiResponse<ClubEventResponse> createClubEvent(@RequestBody @Valid ClubEventCreationRequest request) {
        return ApiResponse.<ClubEventResponse>builder()
                .success(true)
                .data(clubEventService.createClubEvent(request))
                .build();
    }

    @Operation(summary = "Lấy danh sách sự kiện câu lạc bộ")
    @GetMapping
    public ApiResponse<List<ClubEventResponse>> getAllClubEvents() {
        return ApiResponse.<List<ClubEventResponse>>builder()
                .success(true)
                .data(clubEventService.getAllClubEvents())
                .build();
    }

    @Operation(summary = "Lấy sự kiện câu lạc bộ theo ID")
    @GetMapping("/{clubEventId}")
    public ApiResponse<ClubEventResponse> getClubEvents(@PathVariable int clubEventId) {
        return ApiResponse.<ClubEventResponse>builder()
                .data(clubEventService.getClubEvent(clubEventId))
                .success(true)
                .build();
    }

    @Operation(summary = "Cập nhật thông tin sự kiện câu lạc bộ")
    @PutMapping("/{clubEventId}")
    public ApiResponse<ClubEventResponse> updateClubEvent (@PathVariable int clubEventId, @RequestBody ClubEventUpdateRequest request){
        return ApiResponse.<ClubEventResponse>builder()
                .data(clubEventService.updateClubEvent(clubEventId, request))
                .success(true)
                .build();
    }


}
