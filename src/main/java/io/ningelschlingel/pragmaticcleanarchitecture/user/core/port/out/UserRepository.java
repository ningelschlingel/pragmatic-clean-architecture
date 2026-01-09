package io.ningelschlingel.pragmaticcleanarchitecture.user.core.port.out;

import java.util.Optional;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.User;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.UserId;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(UserId id);
    void deleteById(UserId id);
    
}
