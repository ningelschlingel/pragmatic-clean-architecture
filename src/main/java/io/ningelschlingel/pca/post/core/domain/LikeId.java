package io.ningelschlingel.pca.post.core.domain;

import java.util.UUID;

import io.ningelschlingel.pca.shared.core.domain.Identifier;

public final class LikeId extends Identifier {
    
    private LikeId(UUID value) { super(value); }

    public static LikeId of(UUID value) { return new LikeId(value); }
    public static LikeId generate() { return new LikeId(UUID.randomUUID()); }
}
