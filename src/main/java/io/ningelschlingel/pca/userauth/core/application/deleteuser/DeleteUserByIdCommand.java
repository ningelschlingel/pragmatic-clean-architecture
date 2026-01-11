package io.ningelschlingel.pca.userauth.core.application.deleteuser;

import io.ningelschlingel.pca.shared.core.domain.UserId;

public record DeleteUserByIdCommand(
    UserId id
) {}
