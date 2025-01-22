package io.flowtrack.shared.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonConstants {

    public static final String SYSTEM = "system";
    public static final String ADMIN = "admin";
    public static final String ANONYMOUS = "anonymous";
    public static final String UNAUTHENTICATED = "unauthenticated";
    public static final List<String> FORBIDDEN_USERNAMES = Arrays.asList(SYSTEM, ADMIN, ANONYMOUS, UNAUTHENTICATED);

    public static final String CREATE_SCHEMA = "create schema if not exists %s";

}
