package com.miruku.shopping.util;

import com.miruku.shopping.entity.User;
import com.miruku.shopping.exception.AppException;
import com.miruku.shopping.exception.AuthenticateErrorCode;
import com.miruku.shopping.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
@Component
public class TokenValidatorUtil {

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.signerKey}")
    private String SIGN_KEY;

    public String extractUserIdFromToken(HttpServletRequest request) throws ParseException, JOSEException {
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            System.out.println(token);
            return getUserIdFromToken(token);
        }
        throw new IllegalArgumentException("Invalidate Token");
    }

    public String getUserIdFromToken(String token) throws ParseException, JOSEException {
        System.out.println(SIGN_KEY);
        JWSVerifier jwsVerifier = new MACVerifier(SIGN_KEY.getBytes());

        // parse Token
        SignedJWT signedJWT = SignedJWT.parse(token);

        String username = signedJWT.getJWTClaimsSet().getSubject();
        System.out.println(username);
        User user = userRepository.findByUsername(username).orElseThrow(()-> new AppException(AuthenticateErrorCode.NOT_EXIST_USERNAME));
        return user.getId();
    }

    public boolean validateToken(String token) throws JOSEException, ParseException {
        try{
            JWSVerifier jwsVerifier = new MACVerifier(SIGN_KEY.getBytes());

            // parse Token
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.verify(jwsVerifier);
        }
        catch(Exception e){
            throw new AppException(AuthenticateErrorCode.UNAUTHENTICATED);
        }
    }
}
