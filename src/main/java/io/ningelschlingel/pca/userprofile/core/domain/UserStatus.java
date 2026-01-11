package io.ningelschlingel.pca.userprofile.core.domain;

public enum UserStatus {
    PENDING_SYNC,
    ACTIVE,
    SUSPENDED,
    DELETED;

    public boolean canLogin() {
        return this == ACTIVE;
    }
}
