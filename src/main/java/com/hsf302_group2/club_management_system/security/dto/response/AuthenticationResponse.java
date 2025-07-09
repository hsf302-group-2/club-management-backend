package com.hsf302_group2.club_management_system.security.dto.response;

import com.hsf302_group2.club_management_system.user.dto.response.UserResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    UserResponse user;
    String accessToken;
    String refreshToken;
    boolean authenticated;
}
