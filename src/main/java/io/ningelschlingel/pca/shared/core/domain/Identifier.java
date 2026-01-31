package io.ningelschlingel.pca.shared.core.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Base class for UUID wrappers
 * Prevents accidental passing of different id-type
 */
public abstract class Identifier {
    private final UUID value;

    protected Identifier(UUID value) {
        this.value = Objects.requireNonNull(value, "Value must not be null");
    }

    public UUID value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + value + "]";
    }
}