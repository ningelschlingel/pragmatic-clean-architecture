package io.ningelschlingel.pca.post.core.application.togglelike;

import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.shared.core.domain.UserId;

public record ToggleLikeCommand(
    UserId userId,
    PostId postId
) {}
