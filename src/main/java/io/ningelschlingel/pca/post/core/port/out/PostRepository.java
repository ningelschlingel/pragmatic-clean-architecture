package io.ningelschlingel.pca.post.core.port.out;

import java.util.Optional;

import io.ningelschlingel.pca.post.core.domain.Post;
import io.ningelschlingel.pca.post.core.domain.PostId;

public interface PostRepository {

    Post save(Post post);
    Optional<Post> findById(PostId id);
    void deleteById(PostId id);
    
}
