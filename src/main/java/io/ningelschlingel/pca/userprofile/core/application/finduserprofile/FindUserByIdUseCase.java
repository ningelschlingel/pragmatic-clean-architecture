package io.ningelschlingel.pca.userprofile.core.application.finduserprofile;

import io.ningelschlingel.pca.userprofile.core.application.finduserprofile.failure.FindUserFailure;
import io.ningelschlingel.pca.userprofile.core.application.finduserprofile.failure.UserProfileNotFound;
import io.ningelschlingel.pca.userprofile.core.domain.UserProfile;
import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userprofile.core.port.out.UserRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindUserByIdUseCase {
    
    private final UserRepository userRepository;

    public Either<FindUserFailure, UserProfile> execute(UserId id) {
        return userRepository.findById(id)
                .map(Either::<FindUserFailure, UserProfile>right) // wrap found user as Right
                .orElseGet(() -> Either.left(new UserProfileNotFound()));
    }
}
