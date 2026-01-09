package io.ningelschlingel.pragmaticcleanarchitecture.user.infrastructure.web.createuser;

public record CreateUserRequest(
    String email,
    String fullName
) {}
