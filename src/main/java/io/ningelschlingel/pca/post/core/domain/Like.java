package io.ningelschlingel.pca.post.core.domain;

import io.ningelschlingel.pca.shared.core.domain.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Like {
    private final LikeId id;
    private UserId userId;
    private PostId postId;
}
