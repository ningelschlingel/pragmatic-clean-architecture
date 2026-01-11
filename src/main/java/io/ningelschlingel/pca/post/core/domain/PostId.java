package io.ningelschlingel.pca.post.core.domain;

import java.util.UUID;

import io.ningelschlingel.pca.shared.core.domain.Identifier;

public final class PostId extends Identifier {
    
    private PostId(UUID value) { super(value); }

    public static PostId of(UUID value) { return new PostId(value); }
    public static PostId generate() { return new PostId(UUID.randomUUID()); }
}
