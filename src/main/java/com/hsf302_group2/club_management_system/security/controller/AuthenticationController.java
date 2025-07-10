package com.hsf302_group2.club_management_system.security.controller;

import com.hsf302_group2.club_management_system.common.dto.ApiResponse;
import com.hsf302_group2.club_management_system.common.enums.Role;
import com.hsf302_group2.club_management_system.premember.dto.response.PreMemberResponse;
import com.hsf302_group2.club_management_system.security.dto.request.AuthenticationRequest;
import com.hsf302_group2.club_management_system.security.dto.response.AuthenticationResponse;
import com.hsf302_group2.club_management_system.security.service.AuthenticationService;
import com.hsf302_group2.club_management_system.user.dto.request.UserCreationRequest;
import com.hsf302_group2.club_management_system.user.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ApiResponse<PreMemberResponse> signup(@RequestBody @Valid UserCreationRequest request){
        return ApiResponse.<PreMemberResponse>builder()
                .success(true)
                .data(authenticationService.createPreMemberAccount(request, Role.PRE_MEMBER.name()))
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request ){
        return ApiResponse.<AuthenticationResponse>builder()
                .success(true)
                .data(authenticationService.authenticate(request))
                .build();
    }
}
