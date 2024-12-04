package com.example.book_n_go.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String get() {
        return "Admin: Get admin controller";
    }

    @PostMapping
    public String post() {
        return "Admin: Post admin controller";
    }

    @PutMapping
    public String put() {
        return "Admin: Put admin controller";
    }

    @DeleteMapping
    public String delete() {
        return "Admin: Delete admin controller";
    }

}
