package com.gorki.gorkiSpringBoot.entidades;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

        ADMIN_READ("admin:read"),
        ADMIN_UPDATE("admin:update"),
        ADMIN_CREATE("admin:create"),
        ADMIN_DELETE("admin:delete"),
        MANAGER_READ("management:read"),
        MANAGER_UPDATE("management:update"),
        MANAGER_CREATE("management:create"),
        MANAGER_DELETE("management:delete"),
        DEPORTISTA_READ("management:read"),
        DEPORTISTA_UPDATE("management:update"),
        DEPORTISTA_CREATE("management:create"),
        DEPORTISTA_DELETE("management:delete")

        ;

        @Getter
        private final String permission;
    }
