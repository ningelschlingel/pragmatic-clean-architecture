package io.ningelschlingel.pca.post.infrastructure.web.createpost;

import io.ningelschlingel.pca.post.core.application.createpost.CreatePostCommand;
import io.ningelschlingel.pca.post.core.domain.Post;
import io.ningelschlingel.pca.post.core.domain.PostId;

public class CreatePostHttpMapper {

    public static CreatePostCommand fromRequest(CreatePostRequest request) {
        return new CreatePostCommand(PostId.generate(), request.title(), request.content());
    }

    public static CreatePostResponse toResponse(Post post) {
        return new CreatePostResponse(post.getId().value(), post.getTitle(), post.getContent());
    }
}
