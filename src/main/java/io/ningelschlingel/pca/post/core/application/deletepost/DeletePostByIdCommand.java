package io.ningelschlingel.pca.post.core.application.deletepost;

import io.ningelschlingel.pca.post.core.domain.PostId;

public record DeletePostByIdCommand(
    PostId id
) {}
