package io.ningelschlingel.pca.post.core.application.deletepost.failure;

import io.ningelschlingel.pca.post.core.domain.PostFailure;

public sealed interface DeletePostFailure extends PostFailure permits DeletePostNotAllowed {}
