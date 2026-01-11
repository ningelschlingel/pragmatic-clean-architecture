package io.ningelschlingel.pca.shared.core.domain;

public record AuthenticatedUser(UserId principalId, String email) {}
