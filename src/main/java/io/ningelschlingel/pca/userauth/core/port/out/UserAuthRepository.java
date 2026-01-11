package io.ningelschlingel.pca.userauth.core.port.out;

import java.util.Optional;

import io.ningelschlingel.pca.userauth.core.domain.UserAuth;
import io.ningelschlingel.pca.shared.core.domain.UserId;

public interface UserAuthRepository {

    UserAuth save(UserAuth userAuth);
    Optional<UserAuth> findById(UserId id);
    Optional<UserAuth> findByEmail(String email);
    void deleteById(UserId id);
    
}
