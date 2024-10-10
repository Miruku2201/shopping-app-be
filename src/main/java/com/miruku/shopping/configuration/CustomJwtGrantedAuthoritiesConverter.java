package com.miruku.shopping.configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomJwtGrantedAuthoritiesConverter extends JwtAuthenticationConverter {
    public CustomJwtGrantedAuthoritiesConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        this.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(jwt);

            List<String> scopes = jwt.getClaim("scope");

            Collection<GrantedAuthority> roleAuthorities = scopes.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());

            roleAuthorities.addAll(authorities);
            return roleAuthorities;
        });
    }
}
