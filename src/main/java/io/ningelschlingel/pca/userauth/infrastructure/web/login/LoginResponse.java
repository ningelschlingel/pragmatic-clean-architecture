package io.ningelschlingel.pca.userauth.infrastructure.web.login;

import java.util.UUID;

public record LoginResponse(
    UUID id,
    String email
) {}
