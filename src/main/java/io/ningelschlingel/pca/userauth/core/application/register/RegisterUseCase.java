package io.ningelschlingel.pca.userauth.core.application.register;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.shared.infrastructure.security.JwtService;
import io.ningelschlingel.pca.userauth.core.application.register.failure.RegisterUserFailure;
import io.ningelschlingel.pca.userauth.core.application.register.failure.AuthDataInvalid;
import io.ningelschlingel.pca.userauth.core.application.register.failure.UserAuthExists;
import io.ningelschlingel.pca.userauth.core.domain.UserAuth;
import io.ningelschlingel.pca.userauth.core.port.out.PasswordHasher;
import io.ningelschlingel.pca.userauth.core.port.out.UserAuthRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisterUseCase {

    private final UserAuthRepository userAuthRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;

    public Either<RegisterUserFailure, RegisterResult> execute(RegisterCommand command) {
        try {

            if (userAuthRepository.findByEmail(command.email()).isPresent()) {
                return Either.left(new UserAuthExists());
            }

            UserAuth userToSave = new UserAuth(UserId.generate(), command.email(),
                    passwordHasher.hash(command.rawPassword()));
            UserAuth savedUser = userAuthRepository.save(userToSave);

            return Either.right(new RegisterResult(jwtService.createToken(savedUser.getId(), 
                    savedUser.getEmail()), savedUser.getId(), savedUser.getEmail()));

        } catch (Exception e) {
            return Either.left(new AuthDataInvalid());
        }
    }

}
