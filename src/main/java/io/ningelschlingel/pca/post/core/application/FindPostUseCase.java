package io.ningelschlingel.pca.post.core.application;

import io.ningelschlingel.pca.post.core.domain.Post;
import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.post.core.port.out.PostRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

/**
 * Find Post Usecase
 */
@AllArgsConstructor
public class FindPostUseCase {
    
    // Ports
    private final PostRepository postRepository;

    // Failure
    public sealed interface Failure permits PostNotFound {}
    public record PostNotFound() implements Failure {}

    // Action
    public Either<Failure, Post> execute(PostId id) {
        return postRepository.findById(id)
                .map(Either::<Failure, Post>right) // wrap found post as Right
                .orElseGet(() -> Either.left(new PostNotFound()));
    }
}
