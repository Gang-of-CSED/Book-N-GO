package com.example.book_n_go.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.book_n_go.service.JwtService;

@Configuration
public class TestConfig {

    @Bean
    public JwtService jwtService() {
        return new JwtService();
    }
}