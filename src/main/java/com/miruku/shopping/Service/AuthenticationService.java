package com.miruku.shopping.Service;

import com.miruku.shopping.Entity.InvalidateToken;
import com.miruku.shopping.Entity.Role;
import com.miruku.shopping.Entity.User;
import com.miruku.shopping.Exception.AppException;
import com.miruku.shopping.Exception.ErrorCode;
import com.miruku.shopping.Repository.InvalidateTokenRepository;
import com.miruku.shopping.Repository.RoleRepository;
import com.miruku.shopping.Repository.UserRepository;
import com.miruku.shopping.dto.Request.AuthenticationRequest;
import com.miruku.shopping.dto.Request.IntrospectRequest;
import com.miruku.shopping.dto.Response.AuthenticationResponse;
import com.miruku.shopping.dto.Response.IntrospectResponse;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private InvalidateTokenRepository invalidateTokenRepository;

    @Value("${jwt.signerKey}")
    private String SIGN_KEY;

    @Value("${time.expiry.accessToken}")
    private Long expiryAccessToken;

    @Value("${time.expiry.refreshToken}")
    private Long expiryRefreshToken;

    private List<String> buildRoles(User user){
        List<String> listRoles = new ArrayList<>();
        if(!CollectionUtils.isEmpty(user.getRoles())){
            user.getRoles().forEach(
                    role -> {
                        listRoles.add(role.getName());
                    }
            );
        }
        return listRoles;
    }

    private String generateToken(User user, Long expiryToken) throws JOSEException {
        // create header and algorithm
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        // create claim set
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("miruku.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(expiryToken, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildRoles(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        log.warn(SIGN_KEY);

        try{
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
            return jwsObject.serialize();
        }
        catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    // return Access or Refresh token
    private String getToken(User user, boolean isRefresh) throws JOSEException {
        Long expiryTime = (isRefresh) ? expiryRefreshToken : expiryAccessToken;
        return generateToken(user, expiryTime);
    }

    private boolean verify(String token) throws JOSEException, ParseException {
        // get signature to verify
        JWSVerifier jwsVerifier = new MACVerifier(SIGN_KEY.getBytes());

        // parse Token
        SignedJWT signedJWT = SignedJWT.parse(token);

        // verify Token by signature.
        return signedJWT.verify(jwsVerifier);
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        // get signature to verify
        JWSVerifier jwsVerifier = new MACVerifier(SIGN_KEY.getBytes());

        // parse Token
        SignedJWT signedJWT = SignedJWT.parse(token);

        // verify Token by signature.
        boolean verifiedToken = signedJWT.verify(jwsVerifier);

        // check expiry time
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (!(expiryTime.after(new Date()) && verifiedToken)){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if(invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    public IntrospectResponse introspect (IntrospectRequest request) throws ParseException, JOSEException {
        String token = request.getToken();
        boolean valid = true;
        try{
            SignedJWT signedJwt = verifyToken(token);
        }
        catch(AppException e){
            valid = false;
        }
        return IntrospectResponse.builder()
                .isValid(valid)
                .build();
    }

    public AuthenticationResponse authenticate(@NotNull AuthenticationRequest request) throws JOSEException {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new AppException(ErrorCode.NOT_EXIST_USERNAME));
        // match password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isAuthenticate = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isAuthenticate){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Generate token
        return AuthenticationResponse.builder()
                .accessToken(generateToken(user, expiryAccessToken))
                .refreshToken(generateToken(user, expiryRefreshToken))
                .isValidate(true)
                .build();
    }

    public AuthenticationResponse refreshToken(@NotNull IntrospectRequest request) throws ParseException, JOSEException {
        // check valid token
        String token = request.getToken();
        SignedJWT signedJWT = verifyToken(token);

        InvalidateToken invalidateToken = InvalidateToken.builder()
                .tokenId(signedJWT.getJWTClaimsSet().getJWTID())
                .invalidTime(LocalDate.now())
                .build();
        invalidateTokenRepository.save(invalidateToken);

        // Reset token.
        User user = userRepository.findByUsername(signedJWT.getJWTClaimsSet().getSubject()).orElseThrow(()-> new AppException(ErrorCode.NOT_EXIST_USERNAME));
        return AuthenticationResponse.builder()
                .accessToken(generateToken(user, expiryAccessToken))
                .refreshToken(generateToken(user, expiryRefreshToken))
                .isValidate(true)
                .build();

    }

}
