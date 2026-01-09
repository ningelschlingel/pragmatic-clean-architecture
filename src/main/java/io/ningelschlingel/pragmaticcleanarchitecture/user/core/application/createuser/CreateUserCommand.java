package io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.createuser;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.UserId;

public record CreateUserCommand(
    UserId id,
    String email,
    String fullName
) {}
