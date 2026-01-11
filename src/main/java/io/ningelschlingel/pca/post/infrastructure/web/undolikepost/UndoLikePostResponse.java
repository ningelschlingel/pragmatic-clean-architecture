package io.ningelschlingel.pca.post.infrastructure.web.undolikepost;

import java.util.UUID;

public record UndoLikePostResponse(
    UUID id,
    String title,
    String content
) {}
