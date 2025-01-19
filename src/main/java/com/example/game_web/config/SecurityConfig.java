package com.example.game_web.config;

import com.example.game_web.authentication.token.TokenUtils;
import com.example.game_web.authentication.token.handleHeader.TokenFilter;
import com.example.game_web.authentication.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenUtils tokenUtils;
    private final UserService userService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->{
                    auth.requestMatchers(HttpMethod.GET, "/users").hasRole("USER");
                    auth.anyRequest().permitAll();
                })
                .addFilterBefore(
                        new TokenFilter(tokenUtils, userService),
                        AuthorizationFilter.class
                );
        return http.build();
    }

}
