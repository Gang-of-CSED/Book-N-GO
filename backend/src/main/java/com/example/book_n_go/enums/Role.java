package com.example.book_n_go.enums;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    CLIENT(
        Set.of(
            Permission.CLIENT_READ,
            Permission.CLIENT_WRITE,
            Permission.CLIENT_UPDATE,
            Permission.CLIENT_DELETE
        )
    ),

    PROVIDER(
        Set.of(
            Permission.PROVIDER_READ,
            Permission.PROVIDER_WRITE,
            Permission.PROVIDER_UPDATE,
            Permission.PROVIDER_DELETE
        )
    ),

    ADMIN(
        Set.of(
            Permission.ADMIN_READ,
            Permission.ADMIN_WRITE,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_DELETE,

            Permission.CLIENT_READ,
            Permission.CLIENT_WRITE,
            Permission.CLIENT_UPDATE,
            Permission.CLIENT_DELETE,
            
            Permission.PROVIDER_READ,
            Permission.PROVIDER_WRITE,
            Permission.PROVIDER_UPDATE,
            Permission.PROVIDER_DELETE
        )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions().stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}