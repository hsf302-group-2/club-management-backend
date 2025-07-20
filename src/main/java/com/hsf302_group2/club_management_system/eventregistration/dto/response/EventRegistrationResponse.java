package com.hsf302_group2.club_management_system.eventregistration.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRegistrationResponse {
    int id;
    int clubEventId;
    String preMemberId;
    String title;
    String description;
    String location;
    String speaker;
    LocalDate eventDate;
    LocalTime startTime;
    LocalTime endTime;
}
