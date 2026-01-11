package io.ningelschlingel.pca.post.core.application.togglelike;

import io.ningelschlingel.pca.post.core.application.togglelike.failure.LikePostFailure;
import io.ningelschlingel.pca.post.core.application.togglelike.failure.PostNotFoundForLike;
import io.ningelschlingel.pca.post.core.application.togglelike.failure.UserNotFoundForLike;
import io.ningelschlingel.pca.post.core.domain.Like;
import io.ningelschlingel.pca.post.core.port.out.LikeRepository;
import io.ningelschlingel.pca.post.core.port.out.LikerExistencePort;
import io.ningelschlingel.pca.post.core.port.out.PostRepository;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ToggleLikeUseCase {
    
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final LikerExistencePort likerExistencePort;

    public Either<LikePostFailure, Like> execute(ToggleLikeCommand command) {

        if (postRepository.findById(command.postId()).isEmpty()) {
            return Either.left(new PostNotFoundForLike());
        }

        if (!likerExistencePort.exists(command.userId())) {
            return Either.left(new UserNotFoundForLike());
        }

        Like likeToSave = new Like(command);
        Like savedLike = likeRepository.save(likeToSave);
        return Either.right(savedLike);
    }
}
