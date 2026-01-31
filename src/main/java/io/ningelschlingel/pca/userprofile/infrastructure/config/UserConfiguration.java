package io.ningelschlingel.pca.userprofile.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import io.ningelschlingel.pca.userprofile.core.application.CreateUserProfileUseCase;
import io.ningelschlingel.pca.userprofile.core.application.DeleteUserProfileUseCase;
import io.ningelschlingel.pca.userprofile.core.application.FindUserProfileUseCase;
import io.ningelschlingel.pca.userprofile.infrastructure.persistence.JpaUserRepository;

@Configuration
public class UserConfiguration {

    private final JpaUserRepository jpaUserRepository;

    public UserConfiguration(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Bean
    @Transactional
    public CreateUserProfileUseCase createUserUseCase() {
        return new CreateUserProfileUseCase(jpaUserRepository);
    }

    @Bean
    @Transactional
    public FindUserProfileUseCase findUserByIdUseCase() {
        return new FindUserProfileUseCase(jpaUserRepository);
    }

    @Bean
    @Transactional
    public DeleteUserProfileUseCase deleteUserUseCase() {
        return new DeleteUserProfileUseCase(jpaUserRepository);
    }
}
