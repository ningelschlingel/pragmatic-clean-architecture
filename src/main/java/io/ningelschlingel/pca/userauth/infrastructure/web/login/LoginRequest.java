package io.ningelschlingel.pca.userauth.infrastructure.web.login;

public record LoginRequest (
    String email,
    String rawPassword
) {}
