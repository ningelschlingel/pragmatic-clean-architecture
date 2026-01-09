package io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain;

import java.util.UUID;
import java.util.Objects;

public final class UserId {

    private final UUID value;

    public UserId() {
        this.value = UUID.randomUUID(); // auto-generate new ID
    }

    public UserId(UUID value) {
        this.value = Objects.requireNonNull(value, "UserId cannot be null");
    }

    public UUID value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId that)) return false;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
