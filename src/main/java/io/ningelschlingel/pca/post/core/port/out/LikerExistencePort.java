package io.ningelschlingel.pca.post.core.port.out;

import io.ningelschlingel.pca.shared.core.domain.UserId;

public interface LikerExistencePort {
    boolean exists(UserId userId);
}