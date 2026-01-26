package io.ningelschlingel.pca.post.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataLikeRepository extends JpaRepository<LikeEntity, UUID> {
    public List<LikeEntity> findByPostId(UUID postId);
    Optional<LikeEntity> findByLikerIdAndPostId(UUID userId, UUID postId);
}
