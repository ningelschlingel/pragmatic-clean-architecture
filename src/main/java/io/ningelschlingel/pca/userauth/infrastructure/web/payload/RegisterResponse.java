package io.ningelschlingel.pca.userauth.infrastructure.web.payload;

import io.ningelschlingel.pca.shared.core.domain.UserId;

public record RegisterResponse (
    UserId userId,
    String email
    
) {}
