package com.solbeg.wallet.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TestSecurityContext {
    public static final String DEFAULT_USERNAME = "test";
    public static final String DEFAULT_PASSWORD = "123456";
    @Autowired
    private AuthenticationManager authenticationManager;

    public void build() {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(DEFAULT_USERNAME, DEFAULT_PASSWORD);
        Authentication authenticate = authenticationManager.authenticate(authReq);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticate);
    }
}
