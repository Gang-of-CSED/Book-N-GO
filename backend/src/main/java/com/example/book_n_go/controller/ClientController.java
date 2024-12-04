package com.example.book_n_go.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/client")
public class ClientController {

    @GetMapping()
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
