package io.ningelschlingel.pca.post.infrastructure.web.createpost;

import java.util.UUID;

public record CreatePostResponse(
    UUID id,
    String title,
    String content
) {}
