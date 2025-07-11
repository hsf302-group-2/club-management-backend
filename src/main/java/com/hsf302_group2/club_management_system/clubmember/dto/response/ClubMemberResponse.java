package com.hsf302_group2.club_management_system.clubmember.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClubMemberResponse {
    String clubMemberId;
    String userId;
    String email;
    String fullName;
    String userStatus;
    String gender;
    String address;
    String phoneNumber;
    LocalDate dob;
}
