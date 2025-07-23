package com.hsf302_group2.club_management_system.clubpoint.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClubPointHistoryResponse {
    int id;
    int point;
    String reason;
    LocalDateTime calculatedAt;
}
