package io.ningelschlingel.pca.post.infrastructure.web.payload;

public record CreatePostRequest(
    String title,
    String content
) {}
