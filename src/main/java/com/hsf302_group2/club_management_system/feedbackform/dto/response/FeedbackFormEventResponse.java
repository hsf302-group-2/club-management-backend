package com.hsf302_group2.club_management_system.feedbackform.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackFormEventResponse {
    int id;
    int clubEventId;
    String preMemberId;
    int eventRegistrationId;
    String fullName;
    int rating;
    String content;
}
