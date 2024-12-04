package com.example.book_n_go;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.book_n_go.model.Role;
import com.example.book_n_go.model.User;
import com.example.book_n_go.service.AuthService;

@SpringBootApplication
public class BookNGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookNGoApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(AuthService authService) {
        return args -> {
            var admin = new User((long) 0, "admin@email.com", "admin", "7322", Role.ADMIN);
            System.out.println("Admin token:  " + authService.signup(admin.getEmail(), admin.getPassword(), admin.getName(), admin.getRole()));
/*
            var client = new User((long) 1, "nasr@gmail.com", "nasr", "1234", Role.CLIENT);
            System.out.println("Client token:  " + authService.signup(client.getEmail(), client.getPassword(), client.getName(), client.getRole()));

            var provider = new User((long) 2, "mourad@gmail.com", "mourad", "5678", Role.PROVIDER);
            System.out.println("Provider token:  " + authService.signup(provider.getEmail(), provider.getPassword(), provider.getName(), provider.getRole()));
            */
        };
    }
}