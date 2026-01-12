package io.ningelschlingel.pca.post.infrastructure.web.findposts;

import java.util.List;

import io.ningelschlingel.pca.post.infrastructure.web.findpost.FindPostResponse;

public record FindPostsResponse(
    List<FindPostResponse> posts
) {}
