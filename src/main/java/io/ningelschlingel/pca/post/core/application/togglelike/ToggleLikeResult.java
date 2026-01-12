package io.ningelschlingel.pca.post.core.application.togglelike;

import java.util.Optional;

import io.ningelschlingel.pca.post.core.domain.Like;
import io.ningelschlingel.pca.post.core.domain.ToggleAction;

public record ToggleLikeResult(
    ToggleAction toggleAction,
    Optional<Like> likeOpt
) {}
