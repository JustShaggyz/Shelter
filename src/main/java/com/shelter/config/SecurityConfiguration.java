package com.shelter.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.shelter.data.entities.Role.ADMIN;
import static com.shelter.data.entities.Role.USER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/animals/available").hasAnyAuthority(ADMIN.name(), USER.name())
                .requestMatchers("/users/history").hasAnyAuthority(ADMIN.name(), USER.name())
                .requestMatchers("/users/profile").hasAnyAuthority(ADMIN.name(), USER.name())

                .requestMatchers("/animals/onWalk").hasAuthority(ADMIN.name())
                .requestMatchers("/animals/add").hasAuthority(ADMIN.name())
                .requestMatchers("/animals/{animalId}/adopt").hasAuthority(ADMIN.name())
                .requestMatchers("/animals/walk").hasAuthority(ADMIN.name())
                .requestMatchers("/animals/walk/return/{walkId}").hasAuthority(ADMIN.name())
                .requestMatchers("/animals/walk/ongoing").hasAuthority(ADMIN.name())
                .requestMatchers("/users").hasAuthority(ADMIN.name())
                .requestMatchers("/users/volunteers").hasAuthority(ADMIN.name())
                .requestMatchers("/users/{userId}").hasAuthority(ADMIN.name())
                .requestMatchers("/users/{userId}/comments").hasAuthority(ADMIN.name())
                .requestMatchers("/users/{userId}/historyAndComments").hasAuthority(ADMIN.name())
                .requestMatchers("/users/{userId}/history").hasAuthority(ADMIN.name())

                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/animals").permitAll()
                .requestMatchers("/animals/{animalId}").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

        return httpSecurity.build();
    }
}
