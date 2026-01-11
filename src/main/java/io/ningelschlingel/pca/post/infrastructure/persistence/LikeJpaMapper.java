package io.ningelschlingel.pca.post.infrastructure.persistence;

import io.ningelschlingel.pca.post.core.domain.Like;
import io.ningelschlingel.pca.post.core.domain.LikeId;
import io.ningelschlingel.pca.shared.core.domain.UserId;
import io.ningelschlingel.pca.post.core.domain.PostId;

public class LikeJpaMapper {

    static LikeEntity fromDomain(Like like) {
        return new LikeEntity(like.getId().value(), like.getUserId().value(), like.getPostId().value());
    }

    static Like toDomain(LikeEntity entity) { 
        return new Like(LikeId.of(entity.getId()), UserId.of(entity.getLikerId()), PostId.of(entity.getPostId()));
    }
    
}
