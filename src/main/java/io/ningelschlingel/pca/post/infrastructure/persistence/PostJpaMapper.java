package io.ningelschlingel.pca.post.infrastructure.persistence;

import io.ningelschlingel.pca.post.core.domain.Post;
import io.ningelschlingel.pca.post.core.domain.PostId;

public class PostJpaMapper {

    static PostEntity fromDomain(Post post) {
        return new PostEntity(post.getId().value(), post.getTitle(), post.getContent());
    }

    static Post toDomain(PostEntity entity) { 
        return new Post(PostId.of(entity.getId()), entity.getTitle(), entity.getContent());
    }
    
}
