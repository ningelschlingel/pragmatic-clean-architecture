package io.ningelschlingel.pca.userauth.infrastructure.web.register;

import java.util.UUID;

public record RegisterResponse(
    UUID id,
    String email
) {}
