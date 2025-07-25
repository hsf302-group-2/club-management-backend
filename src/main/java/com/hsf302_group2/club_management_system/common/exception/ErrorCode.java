package com.hsf302_group2.club_management_system.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized Exception", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1002, "Email already existed", HttpStatus.CONFLICT),
    PASSWORD_INVALID(1003, "Password is invalid, Password must be at least 8 characters " , HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1004, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1005, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1006, "You do not have permission", HttpStatus.FORBIDDEN),
    PRE_MEMBER_NOT_EXISTED(1007, "Pre member not existed", HttpStatus.NOT_FOUND),
    MEMBER_FORM_EXISTED(1008, "Member form is pending or approving", HttpStatus.BAD_REQUEST),
    MEMBER_FORM_NOT_EXISTED(1009, "Member form not existed", HttpStatus.NOT_FOUND),
    MEMBER_FORM_REVIEWED(1010, "Member form has been reviewed", HttpStatus.BAD_REQUEST),

    ;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatus statusCode;

}
