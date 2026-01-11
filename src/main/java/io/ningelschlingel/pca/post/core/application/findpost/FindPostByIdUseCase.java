package io.ningelschlingel.pca.post.core.application.findpost;

import io.ningelschlingel.pca.post.core.application.findpost.failure.FindPostFailure;
import io.ningelschlingel.pca.post.core.application.findpost.failure.PostNotFound;
import io.ningelschlingel.pca.post.core.domain.Post;
import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.post.core.port.out.PostRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindPostByIdUseCase {
    
    private final PostRepository postRepository;

    public Either<FindPostFailure, Post> execute(PostId id) {
        return postRepository.findById(id)
                .map(Either::<FindPostFailure, Post>right) // wrap found post as Right
                .orElseGet(() -> Either.left(new PostNotFound()));
    }
}
