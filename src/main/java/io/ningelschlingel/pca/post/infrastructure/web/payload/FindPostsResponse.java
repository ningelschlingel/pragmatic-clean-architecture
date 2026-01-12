package io.ningelschlingel.pca.post.infrastructure.web.payload;

import java.util.List;

public record FindPostsResponse(
    List<FindPostResponse> posts
) {}
