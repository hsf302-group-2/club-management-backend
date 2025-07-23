package com.hsf302_group2.club_management_system.activityregistration.dto.request;

import com.hsf302_group2.club_management_system.common.enums.RegistrationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityRegistrationAttendRequest {
    RegistrationStatus status;
}
