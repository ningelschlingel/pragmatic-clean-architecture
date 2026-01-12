package io.ningelschlingel.pca.post.infrastructure.web.payload;

import java.util.UUID;

public record FindPostResponse(
    UUID id,
    String title,
    String content
) {}
