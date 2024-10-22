package com.taskmaster.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final AuthenticationProvider _authenticationProvider;
    private final JwtAuthenticationFilter _jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint _customAuthenticationEntryPoint;
    @Value("${security.cors.allowed-origins}")
    private String allowedOrigins;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this._authenticationProvider = authenticationProvider;
        this._jwtAuthenticationFilter = jwtAuthenticationFilter;
        this._customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/api/auth/**", "/h2-console/**", "/api-docs/**", "/swagger-ui/**").permitAll().requestMatchers("/api/user/**").authenticated().requestMatchers("/api/task/**").authenticated().anyRequest().authenticated()).formLogin(formLogin -> formLogin.disable()).logout(logout -> logout.permitAll()).exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(_customAuthenticationEntryPoint)).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).addFilterBefore(_jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).authenticationProvider(_authenticationProvider);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allowedOrigins));
        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
