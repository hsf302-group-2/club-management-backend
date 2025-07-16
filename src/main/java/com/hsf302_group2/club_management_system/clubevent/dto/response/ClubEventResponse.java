package com.hsf302_group2.club_management_system.clubevent.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClubEventResponse {
    int clubEventId;
    String title;
    String description;
    String location;
    String speaker;
    int currentParticipants;
    LocalDate eventDate;
    LocalTime startTime;
    LocalTime endTime;
}
