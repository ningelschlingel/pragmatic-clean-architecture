package io.ningelschlingel.pca.userauth.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.ningelschlingel.pca.shared.infrastructure.security.JwtService;
import io.ningelschlingel.pca.userauth.core.application.LoginUseCase;
import io.ningelschlingel.pca.userauth.core.application.RegisterUseCase;
import io.ningelschlingel.pca.userauth.core.application.UnregisterUseCase;
import io.ningelschlingel.pca.userauth.core.port.out.UserProfileCreator;
import io.ningelschlingel.pca.userauth.core.port.out.UserProfileDeleter;
import io.ningelschlingel.pca.userauth.infrastructure.persistence.JpaUserAuthRepository;
import io.ningelschlingel.pca.userauth.infrastructure.security.BCryptPasswordHasher;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class UserAuthConfiguration {

    private final JpaUserAuthRepository jpaUserAuthRepository;
    private final BCryptPasswordHasher bCryptPasswordHasher;
    private final JwtService jwtService;
    private final UserProfileCreator userProfileCreator;
    private final UserProfileDeleter userProfileDeleter;



    @Bean
    public RegisterUseCase registerUseCase() {
        return new RegisterUseCase(jpaUserAuthRepository, bCryptPasswordHasher, jwtService, userProfileCreator);
    }

    @Bean
    public UnregisterUseCase unregisterUseCase() {
        return new UnregisterUseCase(jpaUserAuthRepository, userProfileDeleter);
    }

    @Bean
    public LoginUseCase loginUseCase() {
        return new LoginUseCase(jpaUserAuthRepository, bCryptPasswordHasher, jwtService);
    }
}
