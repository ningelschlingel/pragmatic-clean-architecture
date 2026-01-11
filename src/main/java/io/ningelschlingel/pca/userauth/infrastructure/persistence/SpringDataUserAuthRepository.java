package io.ningelschlingel.pca.userauth.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataUserAuthRepository extends JpaRepository<UserAuthEntity, UUID> {
    Optional<UserAuthEntity> findByEmail(String email);
}
