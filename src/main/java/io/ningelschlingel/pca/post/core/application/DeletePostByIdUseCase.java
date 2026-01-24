package io.ningelschlingel.pca.post.core.application;

import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.post.infrastructure.persistence.JpaPostRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeletePostByIdUseCase {

    // Ports
    private final JpaPostRepository jpaRepository;

    // Failure
    public sealed interface Failure permits NotAllowed {}
    public record NotAllowed() implements Failure {}

    public Either<Failure, Void> execute(PostId id){
        jpaRepository.deleteById(id);
        return Either.right(null);
    }
    
}
