package io.ningelschlingel.pca.userprofile.core.application;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userprofile.infrastructure.persistence.JpaUserRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteUserProfileUseCase {

    // Ports
    private final JpaUserRepository jpaRepository;

    // Failure
    public sealed interface Failure permits DeleteUserNotAllowed {}
    public record DeleteUserNotAllowed() implements Failure {}

    // Action
    public Either<Failure, Void> execute(UserId id){
        jpaRepository.deleteById(id);
        return Either.right(null);
    }
    
}
