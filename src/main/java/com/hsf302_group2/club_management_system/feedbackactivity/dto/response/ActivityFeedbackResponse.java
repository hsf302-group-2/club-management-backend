package com.hsf302_group2.club_management_system.feedbackactivity.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityFeedbackResponse {
    int id;
    int clubActivityId;
    String clubMemberId;
    int activityRegistrationId;
    String fullName;
    int rating;
    String content;
}
