package com.miruku.shopping.dto.response;

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
