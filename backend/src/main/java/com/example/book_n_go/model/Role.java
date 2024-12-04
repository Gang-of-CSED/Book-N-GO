package com.example.book_n_go.model;

import java.util.stream.Collectors;

public enum Role {

    CLIENT(
        set.of(
            Permission.CLIENT_READ,
            Permission.CLIENT_WRITE
        )
    ),

    PROVIDER(
        set.of(
            Permission.PROVIDER_READ,
            Permission.PROVIDER_WRITE
        )
    ),

    ADMIN(
        set.of(
            Permission.ADMIN_READ,
            Permission.ADMIN_WRITE,
            Permission.CLIENT_READ,
            Permission.CLIENT_WRITE,
            Permission.PROVIDER_READ,
            Permission.PROVIDER_WRITE
        )
    );

    @Getter
    private final set<Permission> permissions;

    public set<SimpleGrantedAuthority> getAuthorities() {
        set<SimpleGrantedAuthority> authorities = getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
