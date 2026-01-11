package io.ningelschlingel.pca.userauth.core.application.login;

public record LoginCommand(
    String email,
    String rawPassword
) {}
