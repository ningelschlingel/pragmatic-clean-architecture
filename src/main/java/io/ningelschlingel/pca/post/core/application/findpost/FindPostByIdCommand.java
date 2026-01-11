package io.ningelschlingel.pca.post.core.application.findpost;

import io.ningelschlingel.pca.post.core.domain.PostId;

public record FindPostByIdCommand(
    PostId id
) {}
