package io.ningelschlingel.pragmaticcleanarchitecture.user.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.createuser.CreateUserUseCase;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.deleteuser.DeleteUserByIdUseCase;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.finduser.FindUserByIdUseCase;
import io.ningelschlingel.pragmaticcleanarchitecture.user.infrastructure.persistence.JpaUserRepository;

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
