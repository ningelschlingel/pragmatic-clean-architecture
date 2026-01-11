package io.ningelschlingel.pca.userauth.core.application.deleteuser;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.userprofile.core.application.deleteuserprofile.failure.DeleteUserFailure;
import io.ningelschlingel.pca.userprofile.infrastructure.persistence.JpaUserRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteUserByIdUseCase {

    private final JpaUserRepository jpaRepository;

    public Either<DeleteUserFailure, Void> execute(UserId id){
        jpaRepository.deleteById(id);
        return Either.right(null);
    }
    
}
