package io.ningelschlingel.pca.userauth.infrastructure.persistence;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userauth.core.domain.UserAuth;

public class UserAuthJpaMapper {

    static UserAuthEntity fromDomain(UserAuth user) {
        return new UserAuthEntity(user.getId().value(), user.getEmail(), user.getPasswordHash());
    }

    static UserAuth toDomain(UserAuthEntity entity) { 
        return new UserAuth(UserId.of(entity.getUserId()), entity.getEmail(), entity.getPasswordHash());
    }
    
}
