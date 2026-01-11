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

@Repository
@RequiredArgsConstructor
public class JpaLikeRepository implements LikeRepository {

    private final SpringDataLikeRepository springDataLikeRepository;

    @Override
    public Like save(Like like) {
        LikeEntity entity = LikeJpaMapper.fromDomain(like);
        LikeEntity saved = springDataLikeRepository.save(entity);
        return LikeJpaMapper.toDomain(saved);
    }

    @Override
    public List<Like> findByPostId(PostId id) {
        return springDataLikeRepository.findByPostId(id.value()).stream().map(LikeJpaMapper::toDomain).toList();
    }

    @Override
    public void deleteById(LikeId id) {
        springDataLikeRepository.deleteById(id.value());
    }

    @Override
    public Optional<Like> findByLikerIdAndPostId(UserId userId, PostId postId) {
        return springDataLikeRepository.findByLikerIdAndPostId(userId, postId).map(LikeJpaMapper::toDomain);
    }
    
}
