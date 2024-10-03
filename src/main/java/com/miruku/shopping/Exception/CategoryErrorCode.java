package com.miruku.shopping.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CategoryErrorCode implements ErrorCode {
    CATEGORY_NAME_EXISTED(2001, "Category's name is existed", HttpStatus.BAD_REQUEST),
    NOT_EXISTED_CATEGORY(2002, "Category is not existed", HttpStatus.BAD_REQUEST),
    ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
