package io.ningelschlingel.pca.post.infrastructure.persistence;

import java.util.List;

import org.mapstruct.Mapper;

import io.ningelschlingel.pca.post.core.domain.Post;

@Mapper(componentModel = "spring")
public interface PostPersistenceMapper {

    PostEntity fromDomain(Post post);

    List<PostEntity> fromDomain(List<Post> posts);

    Post toDomain(PostEntity entity);

    List<Post> toDomain(List<PostEntity> entities);
}