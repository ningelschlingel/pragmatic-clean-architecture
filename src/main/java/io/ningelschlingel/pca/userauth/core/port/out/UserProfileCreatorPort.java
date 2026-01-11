package io.ningelschlingel.pca.userauth.core.port.out;

import io.ningelschlingel.pca.shared.core.domain.UserId;

public interface UserProfileCreatorPort {

    void createInitialUserProfile(UserId userId);
    
}
