package io.ningelschlingel.pca.post.core.application;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import io.ningelschlingel.pca.post.core.domain.Like;
import io.ningelschlingel.pca.post.core.domain.LikeId;
import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.post.core.domain.ToggleAction;
import io.ningelschlingel.pca.post.core.port.out.LikeRepository;
import io.ningelschlingel.pca.post.core.port.out.LikerExistencePort;
import io.ningelschlingel.pca.post.core.port.out.PostRepository;
import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ToggleLikeUseCase {
    
    // Ports
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final LikerExistencePort likerExistencePort;

    // Command
    public record Command(UserId userId, PostId postId) {}

    // Failure
    public sealed interface Failure permits PostNotFoundForLike, UserNotFoundForLike {}
    public record PostNotFoundForLike() implements Failure {}
    public record UserNotFoundForLike() implements Failure {}

    // Result
    public record Result(ToggleAction toggleAction, Optional<Like> likeOpt) {}

    @Transactional // Ensures atomicity
    public Either<Failure, Result> execute(Command command) {

        // 1. Validation Logic
        if (postRepository.findById(command.postId()).isEmpty()) {
            return Either.left(new PostNotFoundForLike());
        }

        if (!likerExistencePort.exists(command.userId())) {
            return Either.left(new UserNotFoundForLike());
        }

        // 2. Business Logic
        return likeRepository.findByLikerIdAndPostId(command.userId(), command.postId())
            .map(existingLike -> {
                // Case: Delete
                likeRepository.deleteById(existingLike.getId());
                log.info("User {} unliked post {}", command.userId(), command.postId());
                return new Result(ToggleAction.DELETED, Optional.empty());
            })
            .map(Either::<Failure, Result>right)
            .orElseGet(() -> {
                // Case: Create
                Like likeToSave = toDomain(command);
                Like savedLike = likeRepository.save(likeToSave);
                log.info("User {} liked post {}", command.userId(), command.postId());
                return Either.right(new Result(ToggleAction.CREATED, Optional.of(savedLike)));
            });
    }

    // Mapping
    private Like toDomain(Command command) {
        return new Like(LikeId.generate(), command.userId(), command.postId());
    }
}
