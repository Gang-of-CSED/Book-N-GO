package com.example.book_n_go.config;

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
            .csrf
            .disable()
            .authorizeHttpRequests
            .requestMatchers("/auth/signup", "/auth/login")
                .permitAll()

            .requestMatchers("/admin/**").hasRole(ADMIN.name())
            .requestMatchers(GET, "/admin/**").hasAuthority(ADMIN_READ.name())
            .requestMatchers(POST, "/admin/**").hasAuthority(ADMIN_WRITE.name())

            .requestMatchers("/client/**").hasAnyRole(ADMIN.name(), CLIENT.name())
            .requestMatchers(GET, "/client/**").hasAnyAuthority(ADMIN_READ.name(), CLIENT_READ.name())
            .requestMatchers(POST, "/client/**").hasAnyAuthority(ADMIN_WRITE.name(), CLIENT_WRITE.name())
                
            .requestMatchers("/provider/**").hasAnyRole(ADMIN.name(), PROVIDER.name())
            .requestMatchers(GET, "/provider/**").hasAnyAuthority(ADMIN_READ.name(), PROVIDER_READ.name())
            .requestMatchers(POST, "/provider/**").hasAnyAuthority(ADMIN_WRITE.name(), PROVIDER_WRITE.name())

            .anyRequest()
            .authenticated();
        
            return http.build();
    }

}
