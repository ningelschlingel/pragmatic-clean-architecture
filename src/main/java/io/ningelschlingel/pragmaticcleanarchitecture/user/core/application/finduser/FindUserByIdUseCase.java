package io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.finduser;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.finduser.failure.FindUserFailure;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.finduser.failure.UserNotFound;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.User;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.UserId;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.port.out.UserRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindUserByIdUseCase {
    
    private final UserRepository userRepository;

    public Either<FindUserFailure, User> execute(UserId id) {
        return userRepository.findById(id)
                .map(Either::<FindUserFailure, User>right) // wrap found user as Right
                .orElseGet(() -> Either.left(new UserNotFound()));
    }
}
