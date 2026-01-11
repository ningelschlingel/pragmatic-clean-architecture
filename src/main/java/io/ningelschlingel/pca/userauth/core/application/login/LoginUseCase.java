package io.ningelschlingel.pca.userauth.core.application.login;

import java.util.Optional;

import io.ningelschlingel.pca.shared.infrastructure.security.JwtService;
import io.ningelschlingel.pca.userauth.core.application.login.failure.LoginUserFailure;
import io.ningelschlingel.pca.userauth.core.application.login.failure.UserCredentialsInvalid;
import io.ningelschlingel.pca.userauth.core.application.login.failure.UserNotFoundForLogin;
import io.ningelschlingel.pca.userauth.core.domain.UserAuth;
import io.ningelschlingel.pca.userauth.core.port.out.PasswordHasher;
import io.ningelschlingel.pca.userauth.core.port.out.UserAuthRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginUseCase {

    private final UserAuthRepository userAuthRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;
    
    public Either<LoginUserFailure, String> execute(LoginCommand command) {
        Optional<UserAuth> authOpt = userAuthRepository.findByEmail(command.email());
  
        if (authOpt.isEmpty()) {
            return Either.left(new UserNotFoundForLogin());
        }

        UserAuth auth = authOpt.get();

        if (!passwordHasher.matches(command.rawPassword(), auth.getPasswordHash())) {
            return Either.left(new UserCredentialsInvalid());
        }

        return Either.right(jwtService.createToken(auth.getId(), auth.getEmail()));
    }
}
