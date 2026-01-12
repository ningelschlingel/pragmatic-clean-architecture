package io.ningelschlingel.pca.userauth.core.application.register;

import io.ningelschlingel.pca.shared.core.domain.UserId;

public record RegisterResult (
    String jwtToken,
    UserId userId,
    String email
) {}
