package com.hsf302_group2.club_management_system.feedbackactivity.controller;

import com.hsf302_group2.club_management_system.common.dto.ApiResponse;
import com.hsf302_group2.club_management_system.feedbackactivity.dto.request.ActivityFeedbackCreationRequest;
import com.hsf302_group2.club_management_system.feedbackactivity.dto.response.ActivityFeedbackResponse;
import com.hsf302_group2.club_management_system.feedbackactivity.service.ActivityFeedbackService;
import com.hsf302_group2.club_management_system.feedbackevent.dto.request.EventFeedbackCreationRequest;
import com.hsf302_group2.club_management_system.feedbackevent.dto.response.FeedbackFormActivityResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/feedback-activity")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Feedback Activity API", description = "Quản lý feedback cuả các hoạt động clb")
public class ActivityFeedbackController {
    ActivityFeedbackService activityFeedbackService;

    @PostMapping("/activity/{activityRegistrationId}")
    public ApiResponse<ActivityFeedbackResponse> submitFeedbackForActivity(
            @PathVariable int activityRegistrationId, @RequestBody @Valid ActivityFeedbackCreationRequest request) {
        return ApiResponse.<ActivityFeedbackResponse>builder()
                .success(true)
                .data(activityFeedbackService.submitActivityFeedback(request, activityRegistrationId))
                .build();
    }

    @GetMapping("/activity/{clubActivityId}")
    public ApiResponse<List<ActivityFeedbackResponse>> getFeedbackForActivity(@PathVariable int clubActivityId) {
        return ApiResponse.<List<ActivityFeedbackResponse>>builder()
                .success(true)
                .data(activityFeedbackService.getFeedbackFormByClubActivity(clubActivityId))
                .build();
    }
}
