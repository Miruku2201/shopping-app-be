package com.miruku.shopping.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR), // No defined exception
    EXISTED_USERNAME(1001, "User is existed", HttpStatus.BAD_REQUEST),
    UNREGISTERED_ID(1002, "Unregistered id", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1003, "Unauthenticated", HttpStatus.UNAUTHORIZED)
    ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
