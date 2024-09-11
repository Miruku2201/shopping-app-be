package com.miruku.shopping.Exception;

import com.miruku.shopping.dto.Response.ApiResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandle {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandle.class);
    private static final String MIN_ATTRIBUTE = "min";


    // Application Exception
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<?>> handlingAppException(@NotNull AppException exception){
        ErrorCode errorCode = exception.getErrorCode();

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    // Remain Exception
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<?>> handlingException(Exception exception){
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
