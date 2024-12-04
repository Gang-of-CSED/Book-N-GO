package com.example.book_n_go.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/provider")
public class ProviderController {

    @GetMapping
    public String get() {
        return "Provider: Get provider controller";
    }

    @PostMapping
    public String post() {
        return "Provider: Post provider controller";
    }

    @PutMapping
    public String put() {
        return "Provider: Put provider controller";
    }

    @DeleteMapping
    public String delete() {
        return "Provider: Delete provider controller";
    }

}
