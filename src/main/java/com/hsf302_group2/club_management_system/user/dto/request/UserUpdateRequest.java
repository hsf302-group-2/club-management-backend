package com.hsf302_group2.club_management_system.user.dto.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    String fullName;
    boolean active;
}
