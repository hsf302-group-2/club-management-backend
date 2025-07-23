package com.hsf302_group2.club_management_system.dashboard.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatsResponse {
    String title;
    String value;
    String change;
    boolean isGrowing;
}
