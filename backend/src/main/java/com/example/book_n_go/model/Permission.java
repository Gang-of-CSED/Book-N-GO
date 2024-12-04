package com.example.book_n_go.model;

@RequiredArgsConstructor
public enum Permission {

        // Example Permissions

        CLIENT_READ("client:read"),
        CLIENT_WRITE("client:write"),

        PROVIDER_READ("provider:read"),
        PROVIDER_WRITE("provider:write"),

        ADMIN_READ("admin:read"),
        ADMIN_WRITE("admin:write");
    
        @Getter
        private final String permission;
}
