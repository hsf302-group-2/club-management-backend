package com.hsf302_group2.club_management_system.feedbackform.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackFormResponse {
    int id;
    int clubEventId;
    String preMemberId;
    int rating;
    String note;
    LocalDateTime submittedAt;
}
