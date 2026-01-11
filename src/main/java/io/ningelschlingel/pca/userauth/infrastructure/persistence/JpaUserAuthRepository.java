package io.ningelschlingel.pca.userauth.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userauth.core.domain.UserAuth;
import io.ningelschlingel.pca.userauth.core.port.out.UserAuthRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaUserAuthRepository implements UserAuthRepository {

    private final SpringDataUserAuthRepository springDataUserRepository;

    @Override
    public UserAuth save(UserAuth userAuth) {
        UserAuthEntity entity = UserAuthJpaMapper.fromDomain(userAuth);
        UserAuthEntity saved = springDataUserRepository.save(entity);
        return UserAuthJpaMapper.toDomain(saved);
    }

    @Override
    public Optional<UserAuth> findById(UserId id) {
        return springDataUserRepository.findById(id.value()).map(UserAuthJpaMapper::toDomain);
    }

    @Override
    public Optional<UserAuth> findByEmail(String email) {
        return springDataUserRepository.findByEmail(email).map(UserAuthJpaMapper::toDomain);
    }

    @Override
    public void deleteById(UserId id) {
        springDataUserRepository.deleteById(id.value());
    }
    
}
