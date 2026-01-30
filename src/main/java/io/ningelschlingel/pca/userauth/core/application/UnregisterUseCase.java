package io.ningelschlingel.pca.userauth.core.application;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userauth.core.port.out.UserAuthRepository;
import io.ningelschlingel.pca.userauth.core.port.out.UserProfileDeleter;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnregisterUseCase {

    // Ports
    private final UserAuthRepository userAuthRepository;
    private final UserProfileDeleter userProfileDeleter;

    // Failure
    public sealed interface Failure permits DeleteUserNotAllowed {}
    public record DeleteUserNotAllowed() implements Failure {}

    // Action
    public Either<Failure, Void> execute(UserId id){
        userAuthRepository.deleteById(id);
        userProfileDeleter.deleteUserProfile(id);
        return Either.right(null);
    }
    
}
