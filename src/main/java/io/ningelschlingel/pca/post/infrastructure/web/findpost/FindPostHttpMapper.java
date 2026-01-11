package io.ningelschlingel.pca.post.infrastructure.web.findpost;

import io.ningelschlingel.pca.post.core.domain.Post;

public class FindPostHttpMapper {

    public static FindPostResponse toResponse(Post post) {
        return new FindPostResponse(post.getId().value(), post.getTitle(), post.getContent());
    }
}
