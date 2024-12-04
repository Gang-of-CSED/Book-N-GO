package com.example.book_n_go.dto;

import com.example.book_n_go.model.Role;
import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
    private Role role;
}
