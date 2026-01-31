package io.ningelschlingel.pca.userauth.core.application;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import io.ningelschlingel.pca.shared.infrastructure.security.JwtService;
import io.ningelschlingel.pca.userauth.core.domain.UserAuth;
import io.ningelschlingel.pca.userauth.core.port.out.PasswordHasher;
import io.ningelschlingel.pca.userauth.core.port.out.UserAuthRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginUseCase {

    // Ports
    private final UserAuthRepository userAuthRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;
    
    // Command
    public record Command(String email, String rawPassword) {}

    // Failure
    public sealed interface Failure permits UserCredentialsInvalid, UserNotFoundForLogin {}
    public record UserCredentialsInvalid() implements Failure {}
    public record UserNotFoundForLogin() implements Failure {}

    // Result
    public record Result(String token) {};

    // Action
    @Transactional // Pragmatic trade off
    public Either<Failure, Result> execute(Command command) {
        Optional<UserAuth> authOpt = userAuthRepository.findByEmail(command.email());
  
        if (authOpt.isEmpty()) {
            return Either.left(new UserNotFoundForLogin());
        }

        UserAuth auth = authOpt.get();

        if (!passwordHasher.matches(command.rawPassword(), auth.getPasswordHash())) {
            return Either.left(new UserCredentialsInvalid());
        }

        return Either.right(new Result(jwtService.createToken(auth.getId(), auth.getEmail())));
    }

    

}
