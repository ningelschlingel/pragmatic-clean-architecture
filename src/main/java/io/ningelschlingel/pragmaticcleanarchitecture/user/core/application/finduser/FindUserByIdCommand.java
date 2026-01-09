package io.ningelschlingel.pragmaticcleanarchitecture.user.core.application.finduser;

import io.ningelschlingel.pragmaticcleanarchitecture.user.core.domain.UserId;

public record FindUserByIdCommand(
    UserId id
) {}
