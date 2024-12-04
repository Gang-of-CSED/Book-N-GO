package com.example.book_n_go;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookNGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookNGoApplication.class, args);
    }

    // public CommandLineRunner run(AuthService authService) {
    //     return args -> {
    //         System.out.println("Hello World");

    //         var admin = new User((long) 0, "admin@email.com", "admin", "7322", Role.ADMIN);
    //         System.out.println(authService.signup(admin.getEmail(), admin.getPassword(), admin.getName(), admin.getRole()));

    //         var client = new User((long) 1, "nasr@gmail.com", "nasr", "1234", Role.CLIENT);
    //         System.out.println(authService.signup(client.getEmail(), client.getPassword(), client.getName(), client.getRole()));

    //         var provider = new User((long) 2, "mourad@gmail.com", "mourad", "5678", Role.PROVIDER);
    //         System.out.println(authService.signup(provider.getEmail(), provider.getPassword(), provider.getName(), provider.getRole()));
    //     };
    // }
}