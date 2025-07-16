package com.hsf302_group2.club_management_system.memberform.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberFormResponse {
    int id;
    String reason;
    String skill;
    String achievement;
    String expectation;
    String status;
    LocalDateTime createdAt;
    String preMemberId;
    String fullName;
    String gender;
    String address;
    String phoneNumber;
    LocalDateTime dob;

}
