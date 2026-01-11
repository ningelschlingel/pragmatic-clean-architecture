package io.ningelschlingel.pca.userprofile.infrastructure.persistence;

import io.ningelschlingel.pca.userprofile.core.domain.UserProfile;
import io.ningelschlingel.pca.shared.core.domain.UserId;

public class UserJpaMapper {

    static UserEntity fromDomain(UserProfile user) {
        return new UserEntity(user.getId().value(), user.getEmail(), user.getFullName());
    }

    static UserProfile toDomain(UserEntity entity) { 
        return new UserProfile(UserId.of(entity.getId()), entity.getEmail(), entity.getFullName());
    }
    
}
