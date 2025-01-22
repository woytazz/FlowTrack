package io.flowtrack.gateway.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UniqueConstraints {

    public static final String USER_USERNAME = "uc_user_username";
    public static final String ROLE_VALUE = "uc_role_value";

}
