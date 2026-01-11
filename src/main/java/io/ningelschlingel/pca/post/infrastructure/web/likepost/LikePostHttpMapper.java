package io.ningelschlingel.pca.post.infrastructure.web.likepost;

import io.ningelschlingel.pca.post.core.domain.Like;

public class LikePostHttpMapper {

    public static LikePostResponse toResponse(Like like) {
        return new LikePostResponse(like.getId().value(), like.getUserId().value(), like.getPostId().value());
    }
}
