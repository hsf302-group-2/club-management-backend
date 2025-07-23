package com.hsf302_group2.club_management_system.dashboard.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeeklyStatsResponse {
    String weekRange;
    List<StatsResponse> stats;
}
