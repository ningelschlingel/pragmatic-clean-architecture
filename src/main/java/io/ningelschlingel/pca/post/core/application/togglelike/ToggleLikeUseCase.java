package io.ningelschlingel.pca.post.core.application.togglelike;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import io.ningelschlingel.pca.post.core.application.togglelike.failure.LikePostFailure;
import io.ningelschlingel.pca.post.core.application.togglelike.failure.PostNotFoundForLike;
import io.ningelschlingel.pca.post.core.application.togglelike.failure.UserNotFoundForLike;
import io.ningelschlingel.pca.post.core.domain.Like;
import io.ningelschlingel.pca.post.core.domain.ToggleAction;
import io.ningelschlingel.pca.post.core.port.out.LikeRepository;
import io.ningelschlingel.pca.post.core.port.out.LikerExistencePort;
import io.ningelschlingel.pca.post.core.port.out.PostRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ToggleLikeUseCase {
    
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final LikerExistencePort likerExistencePort;

    @Transactional // Ensures atomicity
    public Either<LikePostFailure, ToggleLikeResult> execute(ToggleLikeCommand command) {

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
                return new ToggleLikeResult(ToggleAction.DELETED, Optional.empty());
            })
            .map(Either::<LikePostFailure, ToggleLikeResult>right)
            .orElseGet(() -> {
                // Case: Create
                Like likeToSave = new Like(command);
                Like savedLike = likeRepository.save(likeToSave);
                log.info("User {} liked post {}", command.userId(), command.postId());
                return Either.right(new ToggleLikeResult(ToggleAction.CREATED, Optional.of(savedLike)));
            });
    }
}
