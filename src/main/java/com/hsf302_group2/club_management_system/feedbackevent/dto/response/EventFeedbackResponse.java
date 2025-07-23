package com.hsf302_group2.club_management_system.feedbackevent.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFeedbackResponse {
    int id;
    int clubEventId;
    String preMemberId;
    int eventRegistrationId;
    String fullName;
    int rating;
    String content;
}
