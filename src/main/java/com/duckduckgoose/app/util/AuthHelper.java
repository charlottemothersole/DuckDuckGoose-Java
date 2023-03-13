package com.duckduckgoose.app.util;

import com.duckduckgoose.app.models.auth.MemberDetails;
import com.duckduckgoose.app.models.database.Member;
import com.duckduckgoose.app.services.MemberDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

    private final MemberDetailsService memberDetailsService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthHelper(MemberDetailsService memberDetailsService) {
        this.memberDetailsService = memberDetailsService;
    }

    public BCryptPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(memberDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    public static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_MEMBER"));
    }

    public static Member getAuthenticatedMember() {
        return ((MemberDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).member();
    }
}
