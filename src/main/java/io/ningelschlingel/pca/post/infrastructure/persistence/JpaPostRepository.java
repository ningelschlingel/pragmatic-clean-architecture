package io.ningelschlingel.pca.post.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.ningelschlingel.pca.post.core.domain.Post;
import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.post.core.port.out.PostRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaPostRepository implements PostRepository {

    private final SpringDataPostRepository springDataPostRepository;

    @Override
    public Post save(Post post) {
        PostEntity entity = PostJpaMapper.fromDomain(post);
        PostEntity saved = springDataPostRepository.save(entity);
        return PostJpaMapper.toDomain(saved);
    }

    @Override
    public Optional<Post> findById(PostId id) {
        return springDataPostRepository.findById(id.value()).map(PostJpaMapper::toDomain);
    }

    @Override
    public void deleteById(PostId id) {
        springDataPostRepository.deleteById(id.value());
    }
    
}
