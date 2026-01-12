package io.ningelschlingel.pca.userauth.infrastructure.web.payload;

public record LoginRequest (
    String email,
    String rawPassword
) {}
