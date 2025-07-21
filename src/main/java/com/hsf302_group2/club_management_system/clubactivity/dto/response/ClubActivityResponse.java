package com.hsf302_group2.club_management_system.clubactivity.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClubActivityResponse {
    int clubActivityId;
    String title;
    String description;
    String location;
    String type;
    int currentParticipants;
    LocalDateTime startDate;
    LocalDateTime endDate;
    LocalDateTime registrationDeadline;
}
