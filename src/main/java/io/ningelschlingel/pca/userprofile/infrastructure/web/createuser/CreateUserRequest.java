package io.ningelschlingel.pca.userprofile.infrastructure.web.createuser;

public record CreateUserRequest(
    String email,
    String fullName
) {}
