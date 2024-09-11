package com.miruku.shopping.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miruku.shopping.Exception.ErrorCode;
import com.miruku.shopping.dto.Response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.io.IOException;


/*
* Hàm commence trong lớp JwtAuthenticationEntryPoint có vai trò xử lý các trường hợp không được
* xác thực khi có một yêu cầu không hợp lệ hoặc chưa xác thực từ phía client đến server.
* Cụ thể, hàm này sẽ được gọi khi một người dùng cố gắng truy cập vào một tài nguyên yêu cầu xác thực,
* nhưng họ lại không cung cấp hoặc cung cấp token không hợp lệ.
* */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;
        response.setStatus(errorCode.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
