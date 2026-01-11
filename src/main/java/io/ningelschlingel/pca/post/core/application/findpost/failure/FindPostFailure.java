package io.ningelschlingel.pca.post.core.application.findpost.failure;

import io.ningelschlingel.pca.post.core.domain.PostFailure;

public sealed interface FindPostFailure extends PostFailure permits PostNotFound {}
