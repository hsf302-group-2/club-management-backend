package com.hsf302_group2.club_management_system.feedbackactivity.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityFeedbackCreationRequest {
    int rating;
    String content;
}
