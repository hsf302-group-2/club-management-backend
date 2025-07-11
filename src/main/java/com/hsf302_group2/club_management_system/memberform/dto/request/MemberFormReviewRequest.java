package com.hsf302_group2.club_management_system.memberform.dto.request;

import com.hsf302_group2.club_management_system.common.enums.FormStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberFormReviewRequest {
    FormStatus status;
}
