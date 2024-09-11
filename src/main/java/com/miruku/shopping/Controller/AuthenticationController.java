package com.miruku.shopping.Controller;

import com.miruku.shopping.Service.AuthenticationService;
import com.miruku.shopping.dto.Request.AuthenticationRequest;
import com.miruku.shopping.dto.Response.ApiResponse;
import com.miruku.shopping.dto.Response.AuthenticationResponse;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auths")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    ApiResponse<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest request) throws JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.authenticate(request))
                .build();
    }
}
