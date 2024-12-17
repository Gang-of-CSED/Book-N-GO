package com.example.book_n_go.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

        CLIENT_READ("client:read"),
        CLIENT_WRITE("client:write"),
        CLIENT_DELETE("client:delete"),
        CLIENT_UPDATE("client:update"),

        PROVIDER_READ("provider:read"),
        PROVIDER_WRITE("provider:write"),
        PROVIDER_DELETE("provider:delete"),
        PROVIDER_UPDATE("provider:update"),

        ADMIN_READ("admin:read"),
        ADMIN_WRITE("admin:write"),
        ADMIN_DELETE("admin:delete"),
        ADMIN_UPDATE("admin:update");
    
        @Getter
        private final String permission;
}