package io.ningelschlingel.pca.post.core.application.createpost.failure;

import io.ningelschlingel.pca.post.core.domain.PostFailure;

public sealed interface CreatePostFailure extends PostFailure permits PostNotAllowed, PostDataInvalid {}
