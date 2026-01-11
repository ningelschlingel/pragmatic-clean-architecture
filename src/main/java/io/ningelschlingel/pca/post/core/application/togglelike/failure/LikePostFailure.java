package io.ningelschlingel.pca.post.core.application.togglelike.failure;

import io.ningelschlingel.pca.post.core.domain.PostFailure;

public sealed interface LikePostFailure extends PostFailure permits PostNotFoundForLike, UserNotFoundForLike {}
