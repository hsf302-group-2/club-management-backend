package com.hsf302_group2.club_management_system.clubpoint.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClubPointTotalResponse {
    String clubMemberId;
    String fullName;
    int point;

}
