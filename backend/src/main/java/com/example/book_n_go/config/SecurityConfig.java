package com.example.book_n_go.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.book_n_go.securityFilter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private CorsConfigurationSource corsConfigurationSource;
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/signup", "/auth/login").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		http.cors().configurationSource(corsConfigurationSource).and();
		return http.build();
	}
}