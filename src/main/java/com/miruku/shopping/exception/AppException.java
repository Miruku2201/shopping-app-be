package com.miruku.shopping.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AppException extends RuntimeException{
    private IErrorCode errorCode;

    public AppException(@NotNull IErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AppException(IErrorCode errorCode, Throwable cause){
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
