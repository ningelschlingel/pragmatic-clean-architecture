package io.ningelschlingel.pca.post.core.application.createpost;

import io.ningelschlingel.pca.post.core.domain.PostId;

public record CreatePostCommand(
    PostId id,
    String title,
    String content
) {}
