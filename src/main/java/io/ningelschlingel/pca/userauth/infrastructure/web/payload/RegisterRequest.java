package io.ningelschlingel.pca.userauth.infrastructure.web.payload;

public record RegisterRequest(
    String email,
    String rawPassword
) {}
