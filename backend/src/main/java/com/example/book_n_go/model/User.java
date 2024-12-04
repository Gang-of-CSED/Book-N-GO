package com.example.book_n_go.model;

import java.util.Collection;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    // @Column(unique = true, nullable = false)
    // private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    // @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)

    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
}
