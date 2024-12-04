package com.example.book_n_go.config;

import static org.springframework.http.HttpMethod.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/auth/signup", "/auth/login").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers(GET, "/admin/**").hasAuthority("ADMIN_READ")
                                .requestMatchers(POST, "/admin/**").hasAuthority("ADMIN_WRITE")
                                .requestMatchers("/client/**").hasAnyRole("ADMIN", "CLIENT")
                                .requestMatchers(GET, "/client/**").hasAnyAuthority("ADMIN_READ", "CLIENT_READ")
                                .requestMatchers(POST, "/client/**").hasAnyAuthority("ADMIN_WRITE", "CLIENT_WRITE")
                                .requestMatchers("/provider/**").hasAnyRole("ADMIN", "PROVIDER")
                                .requestMatchers(GET, "/provider/**").hasAnyAuthority("ADMIN_READ", "PROVIDER_READ")
                                .requestMatchers(POST, "/provider/**").hasAnyAuthority("ADMIN_WRITE", "PROVIDER_WRITE")
                                .anyRequest().authenticated()
                );

        return http.build();
    }
}
