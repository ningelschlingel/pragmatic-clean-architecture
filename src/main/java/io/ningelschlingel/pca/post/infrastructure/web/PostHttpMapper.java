package io.ningelschlingel.pca.post.infrastructure.web;

import java.util.List;

import org.mapstruct.Mapper;

import io.ningelschlingel.pca.post.core.application.createpost.CreatePostCommand;
import io.ningelschlingel.pca.post.core.domain.Post;
import io.ningelschlingel.pca.post.infrastructure.web.payload.CreatePostRequest;
import io.ningelschlingel.pca.post.infrastructure.web.payload.FindPostResponse;
import io.ningelschlingel.pca.post.infrastructure.web.payload.FindPostsResponse;

@Mapper
public interface PostHttpMapper {

    FindPostResponse fromDomain(Post post);

    FindPostsResponse fromDomain(List<Post> posts);

    CreatePostCommand toCommand(CreatePostRequest request);
}
