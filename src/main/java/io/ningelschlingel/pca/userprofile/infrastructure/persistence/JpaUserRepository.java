package io.ningelschlingel.pca.userprofile.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userprofile.core.domain.UserProfile;
import io.ningelschlingel.pca.userprofile.core.port.out.UserRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;

    @Override
    public UserProfile save(UserProfile user) {
        UserEntity entity = UserJpaMapper.fromDomain(user);
        UserEntity saved = springDataUserRepository.save(entity);
        return UserJpaMapper.toDomain(saved);
    }

    @Override
    public Optional<UserProfile> findById(UserId id) {
        return springDataUserRepository.findById(id.value()).map(UserJpaMapper::toDomain);
    }

    @Override
    public void deleteById(UserId id) {
        springDataUserRepository.deleteById(id.value());
    }
    
}
