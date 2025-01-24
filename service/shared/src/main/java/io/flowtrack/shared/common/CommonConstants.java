package io.flowtrack.shared.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonConstants {

    public static final String BEARER_JWT_PREFIX = "Bearer ";
    public static final String ACCESS_JWT_CLAIM = "access";
    public static final String ROLES_JWT_CLAIM = "roles";
    public static final String ROLE_SYSTEM = "ROLE_SYSTEM";
    public static final String SYSTEM = "system";
    public static final String ANONYMOUS = "anonymous";
    public static final String UNAUTHENTICATED = "unauthenticated";
    public static final String ADMIN = "admin";
    public static final List<String> FORBIDDEN_USERNAMES = Arrays.asList(SYSTEM, ANONYMOUS, UNAUTHENTICATED, ADMIN);

    public static final String CREATE_SCHEMA = "create schema if not exists %s";

}
