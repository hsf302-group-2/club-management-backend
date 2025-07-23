package com.hsf302_group2.club_management_system.feedbackevent.controller;

import com.hsf302_group2.club_management_system.common.dto.ApiResponse;
import com.hsf302_group2.club_management_system.feedbackevent.dto.request.EventFeedbackCreationRequest;
import com.hsf302_group2.club_management_system.feedbackevent.dto.response.EventFeedbackResponse;
import com.hsf302_group2.club_management_system.feedbackevent.dto.response.FeedbackFormActivityResponse;
import com.hsf302_group2.club_management_system.feedbackevent.service.EventFeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/feedback-event")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Feedback API", description = "Quản lý feedback")
public class EventFeedbackController {
    EventFeedbackService eventFeedbackService;

    @PostMapping("/event/{eventRegistrationId}")
    public ApiResponse<EventFeedbackResponse> submitFeedbackForEvent(
            @PathVariable int eventRegistrationId, @RequestBody @Valid EventFeedbackCreationRequest request) {
        return ApiResponse.<EventFeedbackResponse>builder()
                .success(true)
                .data(eventFeedbackService.submitEventFeedback(request, eventRegistrationId))
                .build();
    }


    @GetMapping("/event/{clubEventId}")
    public ApiResponse<List<EventFeedbackResponse>> getFeedbackForEvent(@PathVariable int clubEventId) {
        return ApiResponse.<List<EventFeedbackResponse>>builder()
                .success(true)
                .data(eventFeedbackService.getFeedbackFormByClubEvent(clubEventId))
                .build();
    }
}
