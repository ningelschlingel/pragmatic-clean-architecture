package io.ningelschlingel.pragmaticcleanarchitecture.user.infrastructure.persistence;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.User;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.UserId;

public class UserJpaMapper {

    static UserEntity fromDomain(User user) {
        return new UserEntity(user.getId().value(), user.getEmail(), user.getFullName());
    }

    static User toDomain(UserEntity entity) { 
        return new User(new UserId(entity.getId()), entity.getEmail(), entity.getFullName());
    }
    
}
