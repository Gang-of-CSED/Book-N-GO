package com.example.book_n_go.model;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {

    CLIENT(
        Set.of(
            Permission.CLIENT_READ,
            Permission.CLIENT_WRITE
        )
    ),

    PROVIDER(
        Set.of(
            Permission.PROVIDER_READ,
            Permission.PROVIDER_WRITE
        )
    ),

    ADMIN(
        Set.of(
            Permission.ADMIN_READ,
            Permission.ADMIN_WRITE,
            Permission.CLIENT_READ,
            Permission.CLIENT_WRITE,
            Permission.PROVIDER_READ,
            Permission.PROVIDER_WRITE
        )
    );

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
