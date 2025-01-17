package com.miruku.shopping.exception;

import com.miruku.shopping.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandle {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandle.class);
    private static final String MIN_ATTRIBUTE = "min";

    private String mapAttributes(String message, Map<String, Object> attributes){
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }


    // Application exception
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<?>> handlingAppException(@NotNull AppException exception){
        IErrorCode errorCode = exception.getErrorCode();

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(apiResponse);
    }

    // exception validation from DTO
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handlingMethodNotValidException(@NotNull MethodArgumentNotValidException exception){
        // get the message is marked in validated annotation.
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        AuthenticateErrorCode errorCode;
        Map<String, Object> attributes = null;
        try{
            errorCode = AuthenticateErrorCode.valueOf(enumKey);
            var constraintViolation = exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);
            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
            log.info(attributes.toString());
        }
        catch(IllegalArgumentException e){
            errorCode = AuthenticateErrorCode.INVALID_MESSAGE;
        }

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(Objects.nonNull(attributes) ?
                    mapAttributes(errorCode.getMessage(), attributes) :
                    errorCode.getMessage())
                .build();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(apiResponse);
    }

    // exception for DB having some problems: drop, not access, timeout, overload resource
    @ExceptionHandler(value = DataAccessResourceFailureException.class)
    public ResponseEntity<ApiResponse<?>> handlingDataAccessResourceFailureException(DataAccessResourceFailureException exception){
        AuthenticateErrorCode errorCode = AuthenticateErrorCode.DISCONNECT_DATABASE;

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage() + ": " + exception.getMessage())
                        .build());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidTokenException(JwtException exception){
        log.warn("Invalid token");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .code(2000)
                        .message("Invalid Token")
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<?>> handlingIllegalArgumentException(IllegalArgumentException exception){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder()
                        .code(2000)
                        .message("Invalid Token")
                        .build());
    }

//    // Remain exception
//    @ExceptionHandler(value = exception.class)
//    public ResponseEntity<ApiResponse<?>> handlingException(exception exception){
//        ApiResponse<?> apiResponse = ApiResponse.builder()
//                .code(AuthenticateErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
//                .message(AuthenticateErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
//                .build();
//        return ResponseEntity.badRequest().body(apiResponse);
//    }
}
