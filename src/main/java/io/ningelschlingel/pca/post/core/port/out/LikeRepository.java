package io.ningelschlingel.pca.post.core.port.out;

import java.util.List;
import java.util.Optional;

import io.ningelschlingel.pca.post.core.domain.Like;
import io.ningelschlingel.pca.post.core.domain.LikeId;
import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.shared.core.domain.UserId;

public interface LikeRepository {

    Like save(Like like);
    List<Like> findByPostId(PostId postId);
    Optional<Like> findByLikerIdAndPostId(UserId userId, PostId postId);
    void deleteById(LikeId id);
}
