package com.hsf302_group2.club_management_system.user.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String email;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    String fullName;
}
