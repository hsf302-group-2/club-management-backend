package com.hsf302_group2.club_management_system.feedbackevent.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFeedbackCreationRequest {
    String content;
    int rating;
}
