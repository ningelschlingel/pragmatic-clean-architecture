package io.ningelschlingel.pca.userprofile.core.port.out;

import java.util.Optional;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userprofile.core.domain.UserProfile;

public interface UserRepository {

    UserProfile save(UserProfile user);
    Optional<UserProfile> findById(UserId id);
    void deleteById(UserId id);
    
}
