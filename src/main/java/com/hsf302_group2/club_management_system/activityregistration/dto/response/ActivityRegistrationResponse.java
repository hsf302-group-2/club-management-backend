package com.hsf302_group2.club_management_system.activityregistration.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityRegistrationResponse {
    int id;
    int clubActivityId;
    String clubMemberId;
    String title;
    String description;
    String location;
    String type;
    LocalDateTime startDate;
    LocalDateTime endDate;
}
