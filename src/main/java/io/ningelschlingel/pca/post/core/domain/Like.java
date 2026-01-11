package io.ningelschlingel.pca.post.core.domain;

import io.ningelschlingel.pca.post.core.application.togglelike.ToggleLikeCommand;
import io.ningelschlingel.pca.shared.core.domain.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Like {
    private final LikeId id;
    private UserId userId;
    private PostId postId;

    public Like(ToggleLikeCommand command){
        this.id = LikeId.generate();
        this.userId = command.userId();
        this.postId = command.postId();
    }
}
