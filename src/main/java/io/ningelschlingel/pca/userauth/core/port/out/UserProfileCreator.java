package io.ningelschlingel.pca.userauth.core.port.out;

import io.ningelschlingel.pca.shared.core.domain.UserId;

public interface UserProfileCreator {
    void createInitialUserProfile(UserId userId, String email, String fullName);
}
