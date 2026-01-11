package io.ningelschlingel.pca.userauth.infrastructure.web.register;

public record RegisterRequest(
    String email,
    String rawPassword
) {}
