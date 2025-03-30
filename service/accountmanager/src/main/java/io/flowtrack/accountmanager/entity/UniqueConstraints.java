package io.flowtrack.accountmanager.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UniqueConstraints {

    public static final String ACCOUNT_EMAIL = "uc_account_email";
}
