package io.ningelschlingel.pca.post.infrastructure.web.createpost;

public record CreatePostRequest(
    String title,
    String content
) {}
