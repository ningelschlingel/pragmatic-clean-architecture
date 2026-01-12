package io.ningelschlingel.pca.post.infrastructure.persistence;

import java.util.List;

import org.mapstruct.Mapper;

import io.ningelschlingel.pca.post.core.domain.Like;

@Mapper(componentModel = "spring")
public interface LikePersistenceMapper {

    LikeEntity fromDomain(Like like);

    List<LikeEntity> fromDomain(List<Like> likes);

    Like toDomain(LikeEntity entity);

    List<Like> toDomain(List<LikeEntity> entities);
    
}
