package io.ningelschlingel.pca.userprofile.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import io.ningelschlingel.pca.userprofile.core.application.createuserprofile.CreateUserUseCase;
import io.ningelschlingel.pca.userprofile.core.application.deleteuserprofile.DeleteUserByIdUseCase;
import io.ningelschlingel.pca.userprofile.core.application.finduserprofile.FindUserByIdUseCase;
import io.ningelschlingel.pca.userprofile.infrastructure.persistence.JpaUserRepository;

@Configuration
public class UserConfiguration {

    private final JpaUserRepository jpaUserRepository;

    public UserConfiguration(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Bean
    @Transactional
    public CreateUserUseCase createUserUseCase() {
        return new CreateUserUseCase(jpaUserRepository);
    }

    @Bean
    @Transactional
    public FindUserByIdUseCase findUserByIdUseCase() {
        return new FindUserByIdUseCase(jpaUserRepository);
    }

    @Bean
    @Transactional
    public DeleteUserByIdUseCase deleteUserUseCase() {
        return new DeleteUserByIdUseCase(jpaUserRepository);
    }
}
