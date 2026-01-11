package io.ningelschlingel.pca.post.core.application.createpost;

import io.ningelschlingel.pca.post.core.application.createpost.failure.CreatePostFailure;
import io.ningelschlingel.pca.post.core.application.createpost.failure.PostDataInvalid;
import io.ningelschlingel.pca.post.core.domain.Post;
import io.ningelschlingel.pca.post.core.port.out.PostRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreatePostUseCase {

    private final PostRepository postRepository;
    
    public Either<CreatePostFailure, Post> execute(CreatePostCommand command) {
        try {
            // 1. Map command to Domain Entity and Save it
            Post postToSave = new Post(command);
            Post savedPost = postRepository.save(postToSave);
    
            // 2. Wrap the result in Either.right
            return Either.right(savedPost);
            
        } catch (Exception e) {
            return Either.left(new PostDataInvalid());
        }
    }

}
