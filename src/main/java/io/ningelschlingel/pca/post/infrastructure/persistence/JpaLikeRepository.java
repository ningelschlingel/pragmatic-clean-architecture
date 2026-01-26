package io.ningelschlingel.pca.post.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import io.ningelschlingel.pca.post.core.domain.Like;
import io.ningelschlingel.pca.post.core.domain.LikeId;
import io.ningelschlingel.pca.post.core.domain.PostId;
import io.ningelschlingel.pca.post.core.port.out.LikeRepository;
import io.ningelschlingel.pca.shared.core.domain.UserId;
import lombok.RequiredArgsConstructor;

/**
 * JPA implementation of the {@link LikeRepository} output port.
 * This adapter coordinates between the domain model and the persistence entities 
 * using Spring Data JPA.
 */
@Repository
@RequiredArgsConstructor
public class JpaLikeRepository implements LikeRepository {

    private final SpringDataLikeRepository springDataLikeRepository;

    @Override
    public Like save(Like like) {
        LikeEntity entity = fromDomain(like);
        LikeEntity saved = springDataLikeRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Like> findByPostId(PostId id) {
        return springDataLikeRepository.findByPostId(id.value()).stream().map(this::toDomain).toList();
    }

    @Override
    public void deleteById(LikeId id) {
        springDataLikeRepository.deleteById(id.value());
    }

    @Override
    public Optional<Like> findByLikerIdAndPostId(UserId userId, PostId postId) {
        return springDataLikeRepository.findByLikerIdAndPostId(userId.value(), postId.value()).map(this::toDomain);
    }

    private Like toDomain(LikeEntity entity) {
        return new Like(
            LikeId.of(entity.getId()),
            UserId.of(entity.getLikerId()),
            PostId.of(entity.getPostId())
        );
    }

    private LikeEntity fromDomain(Like domain) {
        return new LikeEntity(
            domain.getId().value(),
            domain.getUserId().value(),
            domain.getPostId().value()
        );
    }  
}
