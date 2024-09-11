package com.miruku.shopping.Service;

import com.miruku.shopping.Entity.Role;
import com.miruku.shopping.Entity.User;
import com.miruku.shopping.Exception.AppException;
import com.miruku.shopping.Exception.ErrorCode;
import com.miruku.shopping.Repository.RoleRepository;
import com.miruku.shopping.Repository.UserRepository;
import com.miruku.shopping.dto.Request.AuthenticationRequest;
import com.miruku.shopping.dto.Response.AuthenticationResponse;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Value("${jwt.signerKey}")
    private String SIGN_KEY;

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

    private String generateToken(User user) throws JOSEException {
        // create header and algorithm
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        // create claim set
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("miruku.com")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(3600, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("roles", buildRoles(user))
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

    public AuthenticationResponse authenticate(@NotNull AuthenticationRequest request) throws JOSEException {
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(()-> new AppException(ErrorCode.UNREGISTERED_ID));
        // match password
        boolean isAuthenticate = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isAuthenticate){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Generate token
        return AuthenticationResponse.builder()
                .accessToken(generateToken(user))
                .refreshToken("")
                .isValidate(true)
                .build();
    }
}
