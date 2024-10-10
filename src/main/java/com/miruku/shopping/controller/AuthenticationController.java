package com.miruku.shopping.controller;

import com.miruku.shopping.service.AuthenticationService;
import com.miruku.shopping.dto.request.AuthenticationRequest;
import com.miruku.shopping.dto.request.IntrospectRequest;
import com.miruku.shopping.dto.request.LogoutRequest;
import com.miruku.shopping.dto.response.ApiResponse;
import com.miruku.shopping.dto.response.AuthenticationResponse;
import com.miruku.shopping.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auths")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.<IntrospectResponse>builder()
                .result(authenticationService.introspect(request))
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.refreshToken(request))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }
}
