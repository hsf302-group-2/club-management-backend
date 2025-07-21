package com.hsf302_group2.club_management_system.clubactivity.dto.request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClubActivityCreationRequest {
    String title;
    String description;
    String location;
    String type;
    LocalDateTime startDate;
    LocalDateTime endDate;
    LocalDateTime registrationDeadline;
}
