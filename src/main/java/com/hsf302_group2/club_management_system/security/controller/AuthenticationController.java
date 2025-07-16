package com.hsf302_group2.club_management_system.security.controller;

import com.hsf302_group2.club_management_system.common.dto.ApiResponse;
import com.hsf302_group2.club_management_system.common.enums.Role;
import com.hsf302_group2.club_management_system.premember.dto.response.PreMemberResponse;
import com.hsf302_group2.club_management_system.security.dto.request.AuthenticationRequest;
import com.hsf302_group2.club_management_system.security.dto.response.AuthenticationResponse;
import com.hsf302_group2.club_management_system.security.service.AuthenticationService;
import com.hsf302_group2.club_management_system.user.dto.request.UserCreationRequest;
import com.hsf302_group2.club_management_system.user.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @Operation(summary = "Đăng ký tài khoản Pre Member")
    @PostMapping("/signup")
    public ApiResponse<PreMemberResponse> signup(@RequestBody @Valid UserCreationRequest request){
        return ApiResponse.<PreMemberResponse>builder()
                .success(true)
                .data(authenticationService.createPreMemberAccount(request))
                .build();
    }

    @Operation(summary = "Login")
    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request ){
        return ApiResponse.<AuthenticationResponse>builder()
                .success(true)
                .data(authenticationService.authenticate(request))
                .build();
    }

    @Operation(summary = "Xác minh email người dùng sau khi đăng ký")
    @GetMapping("/verify")
    public ApiResponse<String> verifyEmail(@RequestParam("token") String token) {
        return ApiResponse.<String>builder()
                .success(true)
                .data(authenticationService.verifyEmailToken(token))
                .build();
    }

}
