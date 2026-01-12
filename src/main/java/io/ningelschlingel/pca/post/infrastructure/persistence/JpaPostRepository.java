package io.ningelschlingel.pca.post.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.ningelschlingel.pca.post.core.domain.Post;
import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.post.core.port.out.PostRepository;
import lombok.RequiredArgsConstructor;

/**
 * JPA implementation of the {@link PostRepository} output port.
 * This adapter coordinates between the domain model and the persistence entities 
 * using Spring Data JPA.
 */
@Repository
@RequiredArgsConstructor
public class JpaPostRepository implements PostRepository {

    private final SpringDataPostRepository springDataPostRepository;
    private final PostPersistenceMapper postPersistenceMapper;

    @Override
    public Post save(Post post) {
        PostEntity entity = postPersistenceMapper.fromDomain(post);
        PostEntity saved = springDataPostRepository.save(entity);
        return postPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Post> findById(PostId id) {
        return springDataPostRepository.findById(id.value()).map(postPersistenceMapper::toDomain);
    }

    @Override
    public void deleteById(PostId id) {
        springDataPostRepository.deleteById(id.value());
    }
    
}
