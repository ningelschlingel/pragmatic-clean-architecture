package io.ningelschlingel.pca.userauth.core.domain;

public enum UserStatus {
    PENDING_SYNC,
    ACTIVE,
    SUSPENDED,
    DELETED;

    public boolean canLogin() {
        return this == ACTIVE;
    }
}
