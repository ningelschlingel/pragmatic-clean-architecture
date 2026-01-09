package io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.deleteuser;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.UserId;

public record DeleteUserByIdCommand(
    UserId id
) {}
