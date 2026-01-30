package io.ningelschlingel.pca.userauth.core.port.out;

import io.ningelschlingel.pca.shared.core.domain.UserId;

/**
 * UserProfileCreator
 * 
 * Port for deleting a user profile when a user deregisters
 * 
 * Logic is strictly split from auth slice
 * Needs to be called when a user deregisters to also delete the connected profile
 */
public interface UserProfileDeleter {
    void deleteUserProfile(UserId userId);
}
