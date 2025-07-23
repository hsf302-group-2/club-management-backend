package com.hsf302_group2.club_management_system.feedbackform.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackFormRequest {
    String content;
    int rating;
}
