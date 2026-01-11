package io.ningelschlingel.pca.post.infrastructure.web.findpost;

import java.util.UUID;

public record FindPostResponse(
    UUID id,
    String title,
    String content
) {}
