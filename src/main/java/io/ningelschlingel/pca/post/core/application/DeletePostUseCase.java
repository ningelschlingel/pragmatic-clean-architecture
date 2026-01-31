package io.ningelschlingel.pca.post.core.application;

import org.springframework.transaction.annotation.Transactional;

import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.post.core.port.out.PostRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

/**
 * Delete post Usecase
 */
@AllArgsConstructor
public class DeletePostUseCase {

    // Ports
    private final PostRepository postRepository;

    // Failure
    public sealed interface Failure permits NotAllowed {}
    public record NotAllowed() implements Failure {}

    // Action
    @Transactional
    public Either<Failure, Void> execute(PostId id){
        postRepository.deleteById(id);
        return Either.right(null);
    }
    
}
