package io.ningelschlingel.pca.userprofile.core.application;

import org.springframework.transaction.annotation.Transactional;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userprofile.core.domain.UserProfile;
import io.ningelschlingel.pca.userprofile.core.port.out.UserRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateUserProfileUseCase {

    // Port
    private final UserRepository userRepository;

    // Command
    public record Command(UserId id, String email, String fullName) {}

    // Failure
    public sealed interface Failure permits UserExistsAlready, UserDataInvalid {}
    public record UserExistsAlready() implements Failure {}
    public record UserDataInvalid() implements Failure {}
    
    // Action
    @Transactional // Pragmatic trade off
    public Either<Failure, UserProfile> execute(Command command) {
        try {
            UserProfile userToSave = fromCommand(command);
            UserProfile savedUser = userRepository.save(userToSave);
            return Either.right(savedUser);
        } catch (Exception e) {
            return Either.left(new UserDataInvalid());
        }
    }

    // Mapper
    private UserProfile fromCommand(Command command){
        return new UserProfile(command.id(), command.email(), command.fullName());
    }

}
