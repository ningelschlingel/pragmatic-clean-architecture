package io.ningelschlingel.pca.post.core.application;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import io.ningelschlingel.pca.post.core.domain.Post;
import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.post.core.port.out.PostRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

/**
 * Simple, self-contained either-based example UseCase
 * 
 * UseCase defines the contract:
 * - Ports
 * - Command to execute UseCase
 * - Failures for issues the UseCase can encounter
 * - Result of the executed UseCase
 */
@AllArgsConstructor
public class CreatePostUseCase {

    // Ports
    private final PostRepository postRepository;

    // Command
    public record Command(String title,String content) {}

    // Failure
    public sealed interface Failure permits PostNotAllowed, PostDataInvalid {}
    public record PostDataInvalid() implements Failure {}
    public record PostNotAllowed() implements Failure {}

    // Result
    public record Result(UUID postId, String title, String content) {}
    
    // Action
    @Transactional // Pragmatic trade off
    public Either<Failure, Result> execute(Command command) {
        try {
            // 1. Map command to Domain Entity and Save it
            Post postToSave = toDomain(command);
            Post savedPost = postRepository.save(postToSave);
    
            // 2. Wrap the result in Either.right
            return Either.right(toResult(savedPost));
            
        } catch (Exception e) {
            return Either.left(new PostDataInvalid());
        }
    }

    // Mappers
    
    private Post toDomain(Command command) {
        return new Post(PostId.generate(), command.title(), command.content());
    }

    private Result toResult(Post post) {
        return new Result(post.getId().value(), post.getTitle(), post.getContent());
    }
}
