package com.miruku.shopping.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CategoryErrorCode implements IErrorCode {
    CATEGORY_NAME_EXISTED(2001, "Category's name is existed", HttpStatus.BAD_REQUEST),
    NOT_EXISTED_CATEGORY(2002, "Category is not existed", HttpStatus.BAD_REQUEST),
    LARGE_CATEGORY_IS_EXISTED(2003, "Large category with name is existed", HttpStatus.BAD_REQUEST),
    PARENT_CATEGORY_HAS_THIS_CATEGORY(2004, "Parent category has this addition's name", HttpStatus.BAD_REQUEST),
    CATEGORY_HAS_PARENT(2005, "This category has parent", HttpStatus.BAD_REQUEST),
            ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
