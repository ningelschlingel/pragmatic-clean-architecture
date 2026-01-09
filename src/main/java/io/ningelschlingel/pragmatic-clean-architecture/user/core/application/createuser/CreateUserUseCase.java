package io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.createuser;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.createuser.failure.CreateUserFailure;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.createuser.failure.UserDataInvalid;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.User;
import io.ningelschlingel.pragmaticcleanarchitecture.user.core.port.out.UserRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    
    public Either<CreateUserFailure, User> execute(CreateUserCommand command) {
        try {
            // 1. Map command to Domain Entity and Save it
            User userToSave = new User(command);
            User savedUser = userRepository.save(userToSave);
    
            // 2. Wrap the result in Either.right
            return Either.right(savedUser);
            
        } catch (Exception e) {
            return Either.left(new UserDataInvalid());
        }
    }

}
