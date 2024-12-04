package com.example.book_n_go.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/client")
public class AdminController {

    @GetMapping
    public String get() {
        return "Client: Get client controller";
    }

    @PostMapping
    public String post() {
        return "Client: Post client controller";
    }

    @PutMapping
    public String put() {
        return "Client: Put client controller";
    }

    @DeleteMapping
    public String delete() {
        return "Client: Delete client controller";
    }

}
