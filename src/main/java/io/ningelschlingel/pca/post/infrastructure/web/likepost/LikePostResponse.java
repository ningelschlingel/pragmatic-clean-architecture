package io.ningelschlingel.pca.post.infrastructure.web.likepost;

import java.util.UUID;

public record LikePostResponse(
    UUID id,
    UUID userId,
    UUID postId
) {}
