package io.ningelschlingel.pca.post.core.domain;

import java.util.UUID;

import io.ningelschlingel.pca.shared.core.domain.Identifier;

/**
 * Post UUID wrapper
 * Prevents accidental passing of different id-type
 */
public final class PostId extends Identifier {
    
    private PostId(UUID value) { super(value); }

    public static PostId of(UUID value) { return new PostId(value); }
    public static PostId generate() { return new PostId(UUID.randomUUID()); }
}
