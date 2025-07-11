package com.hsf302_group2.club_management_system.memberform.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberFormCreationRequest {
    String reason;
    String skill;
    String achievement;
    String expectation;
}
