package com.example.book_n_go;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
            System.out.println("Hello World");

            var admin = new User("admin@email.com", "admin", "7322", ADMIN);
            System.out.println(authService.signup(admin));

            var client = new User("nasr@gmail.com", "nasr", "1234", CLIENT);
            System.out.println(authService.signup(client));

            var provider = new User("mourad@gmail.com", "mourad", "5678", PROVIDER);
            System.out.println(authService.signup(provider));
        };
    }
}