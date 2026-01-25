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

    @Override
    public Post save(Post post) {
        PostEntity entity = fromDomain(post);
        PostEntity saved = springDataPostRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Post> findById(PostId id) {
        return springDataPostRepository.findById(id.value()).map(this::toDomain);
    }

    @Override
    public void deleteById(PostId id) {
        springDataPostRepository.deleteById(id.value());
    }

    private Post toDomain(PostEntity entity) {
        return new Post(
            PostId.of(entity.getId()),
            entity.getTitle(),
            entity.getContent()
        );
    }

    private PostEntity fromDomain(Post domain) {
        return new PostEntity(
            domain.getId().value(),
            domain.getTitle(),
            domain.getContent()
        );
    } 
}
