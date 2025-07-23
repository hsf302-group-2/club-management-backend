package com.hsf302_group2.club_management_system.activityregistration.dto.response;

import com.hsf302_group2.club_management_system.common.enums.RegistrationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyActivityRegistrationResponse {
    int id;
    int clubActivityId;
    String clubMemberId;
    String title;
    String description;
    String location;
    String type;
    LocalDateTime startDate;
    LocalDateTime endDate;
    RegistrationStatus status;
}
