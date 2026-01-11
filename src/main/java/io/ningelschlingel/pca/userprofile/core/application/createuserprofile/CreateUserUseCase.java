package io.ningelschlingel.pca.userprofile.core.application.createuserprofile;

import io.ningelschlingel.pca.userprofile.core.application.createuserprofile.failure.CreateUserFailure;
import io.ningelschlingel.pca.userprofile.core.application.createuserprofile.failure.UserDataInvalid;
import io.ningelschlingel.pca.userprofile.core.domain.UserProfile;
import io.ningelschlingel.pca.userprofile.core.port.out.UserRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    
    public Either<CreateUserFailure, UserProfile> execute(CreateUserCommand command) {
        try {
            // 1. Map command to Domain Entity and Save it
            UserProfile userToSave = new UserProfile(command);
            UserProfile savedUser = userRepository.save(userToSave);
    
            // 2. Wrap the result in Either.right
            return Either.right(savedUser);
            
        } catch (Exception e) {
            return Either.left(new UserDataInvalid());
        }
    }

}
