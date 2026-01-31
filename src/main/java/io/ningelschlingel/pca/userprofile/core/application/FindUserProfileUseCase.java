package io.ningelschlingel.pca.userprofile.core.application;

import io.ningelschlingel.pca.userprofile.core.domain.UserProfile;

import org.springframework.transaction.annotation.Transactional;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userprofile.core.port.out.UserRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindUserProfileUseCase {
    
    // Ports
    private final UserRepository userRepository;

    // Failure
    public sealed interface Failure permits UserProfileNotFound {}
    public record UserProfileNotFound() implements Failure {}

    // Action
    @Transactional // Pragmatic trade off
    public Either<Failure, UserProfile> execute(UserId id) {
        return userRepository.findById(id)
                .map(Either::<Failure, UserProfile>right) // wrap found user as Right
                .orElseGet(() -> Either.left(new UserProfileNotFound()));
    }
}
