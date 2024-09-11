package com.miruku.shopping.dto.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private boolean isValidate;
    private String accessToken;
    private String refreshToken;
}
