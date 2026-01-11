package io.ningelschlingel.pca.shared.core.domain;

import java.util.UUID;

public final class UserId extends Identifier {
    
    private UserId(UUID value) { super(value); }

    public static UserId of(UUID value) { return new UserId(value); }
    public static UserId generate() { return new UserId(UUID.randomUUID()); }
}
