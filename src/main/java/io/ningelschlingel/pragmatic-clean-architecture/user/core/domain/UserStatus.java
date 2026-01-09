package io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain;

public enum UserStatus {
    PENDING_SYNC,
    ACTIVE,
    SUSPENDED,
    DELETED;

    public boolean canLogin() {
        return this == ACTIVE;
    }
}
