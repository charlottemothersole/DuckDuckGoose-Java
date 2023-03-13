package com.duckduckgoose.app.config;

import com.duckduckgoose.app.util.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConfig {

    private final AuthHelper authHelper;

    @Autowired
    public AuthConfig(AuthHelper authHelper) {
        this.authHelper = authHelper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] staticPaths = {
                "/css/**", "/js/**", "/fonts/**", "/images/**", "/manifests/**",
        };

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/honks", "/members", "/member/*", "/register", "/error").permitAll()
                        .requestMatchers(staticPaths).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authHelper.authenticationProvider());
        return authenticationManagerBuilder.build();
    }
}
