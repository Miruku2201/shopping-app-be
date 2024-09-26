package com.miruku.shopping.Configuration;

import com.miruku.shopping.Exception.AppException;
import com.miruku.shopping.Exception.ErrorCode;
import com.miruku.shopping.Repository.InvalidateTokenRepository;
import com.miruku.shopping.Service.AuthenticationService;
import com.miruku.shopping.dto.Request.IntrospectRequest;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    @Autowired
    private AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Value("${jwt.signerKey}")
    private String SIGN_KEY;

    @Override
    public Jwt decode(String token) throws JwtException {
        try{
            var response = authenticationService.introspect(IntrospectRequest.builder()
                    .token(token)
                    .build());
            if(!response.isValid()){
//                throw new JwtException("Token invalid");
                throw new JwtException("Invalidation Token");
//
            }
        }
        catch (ParseException | JOSEException e) {
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKeySpec = new SecretKeySpec(SIGN_KEY.getBytes(), "HS512");
            nimbusJwtDecoder =  NimbusJwtDecoder
                    .withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }
        return nimbusJwtDecoder.decode(token);
    }

    public static class CustomInvalidTokenException extends JwtException{
        public CustomInvalidTokenException(String message){
            super(message, null);
        }

        @Override
        public synchronized Throwable fillInStackTrace(){
            return this;
        }
    }
}
