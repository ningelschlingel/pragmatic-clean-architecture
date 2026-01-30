package io.ningelschlingel.pca.userauth.core.port.out;

import io.ningelschlingel.pca.shared.core.domain.UserId;

/**
 * UserProfileCreator
 * 
 * Port for creating the user profile when a new user is registered
 * 
 * Logic is strictly split from auth slice
 * Needs to be called when a user registers to also create the user profile
 */
public interface UserProfileCreator {
    void createInitialUserProfile(UserId userId, String email, String fullName);
}
