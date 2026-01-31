package io.ningelschlingel.pca.userauth.core.application;

import org.springframework.transaction.annotation.Transactional;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.shared.infrastructure.security.JwtService;
import io.ningelschlingel.pca.userauth.core.domain.UserAuth;
import io.ningelschlingel.pca.userauth.core.port.out.PasswordHasher;
import io.ningelschlingel.pca.userauth.core.port.out.UserAuthRepository;
import io.ningelschlingel.pca.userauth.core.port.out.UserProfileCreator;
import io.vavr.control.Either;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterUseCase {

    // Ports
    private final UserAuthRepository userAuthRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;
    private final UserProfileCreator userProfileCreator;

    // Command
    public record Command(@Email String email, @Size(min = 8) String rawPassword, String fullName) {}

    // Failure
    public sealed interface Failure permits UserAuthExists, AuthDataInvalid {}
    public record AuthDataInvalid() implements Failure {}
    public record UserAuthExists() implements Failure {}

    // Result
    public record RegisterResult (String jwtToken, UserId userId, String email) {}

    // Action
    @Transactional // Pragmatic trade off
    public Either<Failure, RegisterResult> execute(Command command) {
        try {

            if (userAuthRepository.findByEmail(command.email()).isPresent()) {
                return Either.left(new UserAuthExists());
            }

            UserAuth userToSave = new UserAuth(UserId.generate(), command.email(),
                    passwordHasher.hash(command.rawPassword()));
            UserAuth savedUser = userAuthRepository.save(userToSave);

            userProfileCreator.createInitialUserProfile(savedUser.getId(), savedUser.getEmail(), command.fullName());

            return Either.right(new RegisterResult(jwtService.createToken(savedUser.getId(), 
                    savedUser.getEmail()), savedUser.getId(), savedUser.getEmail()));

        } catch (Exception e) {
            return Either.left(new AuthDataInvalid());
        }
    }

}
