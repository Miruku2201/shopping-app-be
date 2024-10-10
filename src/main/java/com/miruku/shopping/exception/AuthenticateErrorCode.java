package com.miruku.shopping.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AuthenticateErrorCode implements IErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR), // No defined exception
    EXISTED_USERNAME(1001, "User is existed", HttpStatus.BAD_REQUEST),
    UNREGISTERED_ID(1002, "Unregistered id", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1003, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    NOT_EXIST_USERNAME(1004, "Username does not exist", HttpStatus.BAD_REQUEST),
    INVALID_MESSAGE(1005, "Messages are not existed or invalided", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1006, "Invalid password. Password must have at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1007, "Invalid username.", HttpStatus.BAD_REQUEST),
    DISCONNECT_DATABASE(1008, "Database has been disconnected", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_DOB(1009, "Invalid date of birth. Your age must be at least {min}", HttpStatus.BAD_REQUEST)
    ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
